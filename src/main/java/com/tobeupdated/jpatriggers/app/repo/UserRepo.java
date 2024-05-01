package com.tobeupdated.jpatriggers.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeupdated.jpatriggers.app.model.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
