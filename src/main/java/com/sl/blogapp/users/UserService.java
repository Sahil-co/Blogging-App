package com.sl.blogapp.users;

import com.sl.blogapp.users.dtos.CreateUserRequestDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequestDto req){

        UserEntity newUser = modelMapper.map(req, UserEntity.class);

        //TODO: encrypt and save password as well

        newUser.setPassword(passwordEncoder.encode(req.getPassword()));

        //System.out.println(newUser.getPassword());
        //** other way instead of using modelmapper
//        var user = UserEntity.builder()
//                .username(req.getUsername())
////                .password(req.getPassword() //ToDo: password encrypt
//                .email(req.getEmail())
//                .build();

        return usersRepository.save(newUser);
    }

    public UserEntity getUserByName(String username){
        return usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity getUserById(Long id){
        return usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    public UserEntity loginUser(String username, String password){
        var user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        //TODO: match password

        var passwordMatch = passwordEncoder.matches(password, user.getPassword());

        if(!passwordMatch) {
            throw new InvalidUserCredentialsException();
        }

        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username){
            super("User "+username+" not found");
        }

        public UserNotFoundException(Long id){
            super("User with "+id+" not found");
        }
    }

    public static class InvalidUserCredentialsException extends IllegalArgumentException{
        public InvalidUserCredentialsException(){
            super("Invalid Username or password combination");
        }
    }
}
