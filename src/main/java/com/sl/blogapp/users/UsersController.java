package com.sl.blogapp.users;

import com.sl.blogapp.security.JWTService;
import com.sl.blogapp.users.dtos.CreateUserRequestDto;
import com.sl.blogapp.users.dtos.LoginUserRequestDto;
import com.sl.blogapp.users.dtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sl.blogapp.users.dtos.commondtos.ErrorResponse;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final JWTService jwtService;

    public UsersController(UserService userService, ModelMapper modelMapper, JWTService jwtService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signupUser(@RequestBody CreateUserRequestDto req){
        UserEntity savedUser = userService.createUser(req);
        URI savedUserURI = URI.create("/users/"+savedUser.getId());
        var savedUserResponse = modelMapper.map(savedUser, UserResponseDto.class);

        savedUserResponse.setToken(
                jwtService.createJWT(savedUser.getId())
        );
        return ResponseEntity.created(savedUserURI)
                .body(savedUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginUserRequestDto req){
        UserEntity user = userService.loginUser(req.getUsername(), req.getPassword());

        var loginUser = modelMapper.map(user, UserResponseDto.class);

        loginUser.setToken(
                jwtService.createJWT(user.getId())
        );

        return ResponseEntity.ok(loginUser);
    }


    @ExceptionHandler({UserService.UserNotFoundException.class, UserService.InvalidUserCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex){

        String message;
        HttpStatus status;

        if(ex instanceof UserService.UserNotFoundException){
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else if(ex instanceof UserService.InvalidUserCredentialsException){
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        }
        else{
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
