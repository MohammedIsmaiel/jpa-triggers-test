package com.tobeupdated.jpatriggers.app.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tobeupdated.jpatriggers.app.model.entity.User;
import com.tobeupdated.jpatriggers.app.model.entity.UserData;
import com.tobeupdated.jpatriggers.app.repo.UserRepo;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserListener implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @PostPersist
    public void onInsert(User user) {
        // Logic to execute after user insertion
        log.info("User inserted:------------------------------- " + user.getId());
        var userRepo = context.getBean(UserRepo.class);
        var userData = new UserData();
        userData.setAdditionalData("aditional data");
        userData.setUser(user);
        user.setData(userData);
        userRepo.save(user);
    }

    @PostUpdate
    public void onUpdate(User user) {
        // Logic to execute after user update
        log.info("User updated:-------------------------------- " + user.getId());
    }
}
