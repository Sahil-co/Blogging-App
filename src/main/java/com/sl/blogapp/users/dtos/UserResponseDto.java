package com.sl.blogapp.users.dtos;


import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String bio;
    private String image;
    private String token;
}
