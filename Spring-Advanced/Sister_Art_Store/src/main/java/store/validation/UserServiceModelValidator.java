package store.validation;


import store.model.entity.Product;
import store.model.entity.User;
import store.model.service.UserServiceModel;

public interface UserServiceModelValidator {

    boolean isValid(User user);

    boolean isValid(UserServiceModel user);

}
