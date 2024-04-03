package com.sl.blogapp.users;

import com.sl.blogapp.users.dtos.CreateUserRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UsersServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void createUserTest(){
        var user = userService.createUser(new CreateUserRequestDto(
                "Sahil",
                "sahil@12",
                "sahil12@gmail.com"
        ));


        Assertions.assertNotNull(user);
        Assertions.assertEquals("Sahil", user.getUsername());
    }
}
