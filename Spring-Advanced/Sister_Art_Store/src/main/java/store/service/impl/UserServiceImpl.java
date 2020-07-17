package store.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import store.error.PasswordDontMatchException;
import store.model.entity.Role;
import store.model.entity.User;
import store.model.service.UserServiceModel;
import store.repository.UserRepository;
import store.service.RoleService;
import store.service.UserService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Incorrect user or password"));
    }

    @Override
    public UserServiceModel findUserByUserName(String username)  throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {

        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("Username not found!"));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordDontMatchException("Incorrect password!");
        }

        user.setPassword(userServiceModel.getPassword() != null ?
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) :
                user.getPassword());
        user.setEmail(userServiceModel.getEmail());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll().stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setUserRole(String id, String role) throws UsernameNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Not such user"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        if(role.equals("user")){
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        } else if (role.equals("admin")) {
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));
        }


        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public void deleteUser(String id) {
        User user = this.userRepository.findUserById(id);
        user.getAuthorities().clear();
        this.userRepository.delete(user);
    }
//     @GetMapping("/api/users")
//    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public List<UsersViewModel> allUsers() {
//
//        return this.userService.findAllUsers()
//                .stream()
//                .map(serviceModel -> this.modelMapper.map(serviceModel, UsersViewModel.class))
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/api/users/find")
//    @ResponseBody
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public UsersViewModel allUsers(@RequestParam(USERNAME) String username) {
//
//        UserServiceModel byUsername = this.userService.findByUsername(username);
//
//        return byUsername == null ? new UsersViewModel()
//                : this.modelMapper.map(byUsername, UsersViewModel.class);
//    }
}
