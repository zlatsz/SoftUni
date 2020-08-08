package store.validation;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import store.model.service.UserServiceModel;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceModelValidatorTests {


    UserServiceModel user;
    @Mock
    private UserServiceModelValidator validator;

    @Before
    public void setUp() {
        user = new UserServiceModel();
        validator = new UserServiceModelValidatorImpl();
        user.setUsername("Gosho");
        user.setPassword("1111");
        user.setEmail("mail@mail.bg");
    }

    @Test
    public void isValid_WhenNameNull_ShouldFalse() {
        user.setUsername(null);
        boolean result = validator.isValid(user);
        assertFalse(result);
    }

    @Test
    public void isValid_WhenPasswordNull_ShouldFalse() {
        user.setPassword(null);
        boolean result = validator.isValid(user);
        assertFalse(result);
    }

    @Test
    public void isValid_WhenEmailNull_ShouldFalse() {
        user.setEmail(null);
        boolean result = validator.isValid(user);
        assertFalse(result);
    }
    @Test
    public void isValid_WhenEmailValid_ShouldTrue() {
        boolean result = validator.isValid(user);
        assertTrue(result);
    }

}
