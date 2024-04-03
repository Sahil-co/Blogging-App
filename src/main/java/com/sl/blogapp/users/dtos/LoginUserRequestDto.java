package com.sl.blogapp.users.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Data
@Setter(AccessLevel.NONE)
public class LoginUserRequestDto {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
