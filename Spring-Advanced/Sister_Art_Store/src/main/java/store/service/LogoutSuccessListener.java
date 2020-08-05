package store.service;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LogoutSuccessListener implements ApplicationListener<LogoutSuccessEvent> {

    @Override
    public void onApplicationEvent(LogoutSuccessEvent evt) {
        String login = evt.getAuthentication().getName();
        System.out.println(login + " has just logged out");
    }
}
