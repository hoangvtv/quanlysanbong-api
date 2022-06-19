package com.example.doannmcnpm.repository;

import com.example.doannmcnpm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
//    User findByPhone(String phone);

}
