package com.example.demo.controller;

import com.example.demo.exceptions.PasswordMismatchException;
import com.example.demo.exceptions.UserExistsException;
import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserHtmlController {

    private final UserService userService;

    public UserHtmlController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("userDto",new UserDto());
        return "newUserForm";
    }

    @PostMapping("/addUser")
    public String AddedUser(@ModelAttribute("user") UserDto user, Model model){

        try {
            userService.createUser(user);
            return "redirect:/index";
        } catch (UserExistsException e) {
            model.addAttribute("userDto",user);
            model.addAttribute("userExistsError",true);
            return "newUserForm";
        } catch (PasswordMismatchException e) {
            model.addAttribute("userDto",user);
            model.addAttribute("wrongPasswordError",true);
            return "newUserForm";
        }
    }
}
