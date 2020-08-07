package store.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import store.error.UserNameTakenException;
import store.error.UserNotFoundException;
import store.error.UserWrongCredentialsException;
import store.model.entity.Role;
import store.model.entity.User;
import store.model.service.RoleServiceModel;
import store.model.service.UserServiceModel;
import store.repository.UserRepository;
import store.service.impl.UserServiceImpl;
import store.validation.UserServiceModelValidator;
import store.validation.UserServiceModelValidatorImpl;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl service;
    @Mock
    UserRepository mockUserRepository;
    @Mock
    RoleService roleService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    BCryptPasswordEncoder encoder;
    @Mock
    UserServiceModelValidator validator;

    User user;
    UserServiceModel model;

    @Before
    public void setUp() {
        user = new User();
        model = new UserServiceModel();
        model.setUsername("name");
        model.setEmail("email@abv.bg");
        model.setPassword("1234");
        model.setAuthorities(Set.of(new RoleServiceModel()));
        user.setUsername("pesho");

        model.setEmail("email@abv.bg");
        user.setPassword("1234");
        user.setAuthorities(Set.of(new Role()));

    }

    @Test(expected = UserWrongCredentialsException.class)
    public void registerUser_WhenNotValid_ShouldThrow() {
        Mockito.when(validator.isValid(model))
                .thenReturn(false);
        service.registerUser(model);
    }

    @Test
    public void registerUser_WhenAllOk_ShouldWork() {
        Mockito.when(validator.isValid(model))
                .thenReturn(true);
        Mockito.when(modelMapper.map(model, User.class))
                .thenReturn(user);
        Mockito.when(modelMapper.map(user, UserServiceModel.class))
                .thenReturn(model);
        Mockito.when(mockUserRepository.saveAndFlush(user))
                .thenReturn(user);
        UserServiceModel result = service.registerUser(model);

        Mockito.verify(modelMapper).map(model, User.class);
        Mockito.verify(mockUserRepository).saveAndFlush(user);
        Mockito.verify(modelMapper).map(user, UserServiceModel.class);

        assertEquals(model, result);
    }

    @Test
    public void loadUserByUsername_WhenUserExist_ShouldWork() {
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenReturn(Optional.of(user));
        User userResult = (User) service.loadUserByUsername("name");
        assertEquals(user, userResult);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_WhenNotUserExist_ShouldThrow() {
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenThrow(UsernameNotFoundException.class);
        service.loadUserByUsername("name");
    }

    @Test
    public void findByUsername_WhenUserExist_ShouldWork() {
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenReturn(Optional.of(user));
        User userResult = (User) service.loadUserByUsername("name");
        assertEquals(user, userResult);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByUsername_WhenNotUserExist_ShouldThrow() {
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenThrow(UsernameNotFoundException.class);
        service.loadUserByUsername("name");
    }

    @Test
    public void editUserProfile_WhenAllOk_ShouldWork() {
        user.setPassword("1234");
        model.setUsername("name");
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenReturn(Optional.of(user));
        Mockito.when(encoder.matches("1234", "1234"))
                .thenReturn(true);
        Mockito.when(encoder.encode("1234"))
                .thenReturn("1234");
        service.editUserProfile(model, "1234");
        assertEquals(user.getEmail(), model.getEmail());
    }

    @Test(expected = UserNameTakenException.class)
    public void registerUser_WhenUserAlreadyExist_ShouldThrow() {
        Mockito.when(validator.isValid(model))
                .thenReturn(true);
        Mockito.when(mockUserRepository.findByUsername("name"))
                .thenThrow(UserNameTakenException.class);
        service.registerUser(model);
    }

    @Test
    public void findAllUsers_WhenThereIsUsers_ShouldWork(){
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(mockUserRepository.findAll())
                .thenReturn(users);
        int result = service.findAllUsers().size();
        assertEquals(users.size(), result);
    }

    @Test
    public void getUserProfile_whenNoUserProfile_shouldReturnEmptyList() {
        List<UserServiceModel> userProfileServiceModels = service.findAllUsers();
        Assertions.assertEquals(0, userProfileServiceModels.size());
    }

    @Test
    public void testUserFound() {
        when(mockUserRepository.findByUsername(model.getUsername())).
                thenReturn(Optional.of(user));

        UserDetails actualDetails = service.loadUserByUsername(model.getUsername());

        Assertions.assertEquals(user.getUsername(), actualDetails.getUsername());
    }
}