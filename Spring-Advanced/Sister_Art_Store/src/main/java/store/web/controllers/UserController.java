package store.web.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import store.model.binding.UserEditBindingModel;
import store.model.binding.UserRegisterBindingModel;
import store.model.service.RoleServiceModel;
import store.model.service.UserServiceModel;
import store.model.view.UserAllViewModel;
import store.model.view.UserProfileViewModel;
import store.service.UserService;
import store.validation.UserEditValidator;
import store.validation.UserRegisterValidator;
import store.web.annotations.PageTitle;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{

    private final UserService userService;
    private final UserRegisterValidator userRegisterValidator;
    private final UserEditValidator userEditValidator;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, UserRegisterValidator userRegisterValidator, UserEditValidator userEditValidator, ModelMapper mapper) {
        this.userService = userService;
        this.userRegisterValidator = userRegisterValidator;
        this.userEditValidator = userEditValidator;
        this.mapper = mapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register")
    public ModelAndView register(ModelAndView modelAndView,
                                 @ModelAttribute(name = "model") UserRegisterBindingModel model) {
        modelAndView.addObject("model", model);
        return view("users/register", modelAndView);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(ModelAndView modelAndView,
                                  @ModelAttribute (name = "model") UserRegisterBindingModel model,
                                  BindingResult bindingResult) {
        this.userRegisterValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            model.setPassword(null);
            model.setConfirmPassword(null);
            modelAndView.addObject("model", model);
            return view("users/register", modelAndView);
        }
        UserServiceModel serviceModel = mapper.map(model, UserServiceModel.class);
        userService.registerUser(serviceModel);

        return redirect("/users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public ModelAndView login() {
        return view("users/login");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("User Details")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView){
        UserServiceModel user = this.userService.findUserByUserName(principal.getName());
        UserProfileViewModel model = mapper.map(user, UserProfileViewModel.class);
        modelAndView.addObject("model", model);
        return view("users/profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit User")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView){
        UserServiceModel user = this.userService.findUserByUserName(principal.getName());
        UserEditBindingModel model = mapper.map(user, UserEditBindingModel.class);
        modelAndView.addObject("model", model);

        return view("users/edit-profile", modelAndView);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@Valid @ModelAttribute(name = "model") UserEditBindingModel userEditBindingModel,
                                           BindingResult bindingResult){

        this.userEditValidator.validate(userEditBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return view("users/edit-profile");
        }

        UserServiceModel userServiceModel = mapper.map(userEditBindingModel, UserServiceModel.class);
        this.userService.editUserProfile(userServiceModel, userEditBindingModel.getOldPassword());

        return redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Users")
    public ModelAndView allUsers(ModelAndView modelAndView){
        List<UserAllViewModel> users = userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel user = mapper.map(u, UserAllViewModel.class);
                    Set<String> authorities = getAuthoritiesToString(u);
                    user.setAuthorities(authorities);
                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);
        return view("users/users-all", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView setUser(@PathVariable String id) {
        userService.setUserRole(id, "user");

        return redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView setAdmin(@PathVariable String id) {
        userService.setUserRole(id, "admin");

        return redirect("/users/all");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteMessage(@PathVariable String id) {
        this.userService.deleteUser(id);

        return redirect("/users/all");
    }

    private Set<String> getAuthoritiesToString(UserServiceModel userServiceModel) {
        return userServiceModel.getAuthorities()
                .stream()
                .map(RoleServiceModel::getAuthority)
                .collect(Collectors.toSet());
    }


}
