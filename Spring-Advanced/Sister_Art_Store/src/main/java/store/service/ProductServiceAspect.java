package store.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import store.model.service.ProductServiceModel;


@Aspect
@Component
public class ProductServiceAspect {

    @Before(value = "execution(* store.service.impl.ProductServiceImpl.*(..)) && args(productServiceModel)")
    public void beforeAdvice(JoinPoint joinPoint, ProductServiceModel productServiceModel) {
        System.out.println("Преди метод: " + joinPoint.getSignature());

        System.out.println("Създава се продукт с име - " + productServiceModel.getName() + " и цена - " + productServiceModel.getPrice() + " лв.");
    }

    @After(value = "execution(* store.service.impl.ProductServiceImpl.*(..)) && args(productServiceModel)")
    public void afterAdvice(JoinPoint joinPoint, ProductServiceModel productServiceModel) {
        System.out.println("След метод: " + joinPoint.getSignature());

        System.out.println("Успешно е създаден продукт с име - " + productServiceModel.getName() + " и цена - " + productServiceModel.getPrice() + " лв.");
    }
}