package com.sl.blogapp.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Order(1)
    public void can_create_users(){
        var user = UserEntity.builder()
                .username("Sahil")
                .email("sahil12@gmail.com")
                .build();

        usersRepository.save(user);
    }

    @Test
    @Order(2)
    public void can_find_users(){
        var user = UserEntity.builder()
                .username("Sahil")
                .email("sahil12@gmail.com")
                .build();

        usersRepository.save(user);

        var users = usersRepository.findAll();

        Assertions.assertEquals(1, users.size());
    }
}
