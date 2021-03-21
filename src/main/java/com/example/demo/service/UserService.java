package com.example.demo.service;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepo;
import com.example.demo.exceptions.PasswordMismatchException;
import com.example.demo.exceptions.UserExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("there is no user with this username: " + username));
    }

    public User findUserEntity() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User: " + user + "cannot be found"));
    }

    public UserDto createUser(UserDto userDto) throws UserExistsException, PasswordMismatchException {

        if (userRepo.existsByUsername(userDto.getUsername())) {
            throw new UserExistsException();
        }
        else if(!userDto.getPassword().equals(userDto.getPassword1())){
            throw new PasswordMismatchException();
        }
//        return map(userRepo.save(map(userDto)));   //do weryfikacji podwoijne mapowanie
        User user = map(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return map(userRepo.save(user));
    }

    private User map(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto map(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
