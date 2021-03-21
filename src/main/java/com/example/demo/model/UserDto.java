package com.example.demo.model;

import com.example.demo.domain.model.Order;
import lombok.Data;

import javax.persistence.OneToMany;
import java.util.List;
@Data
public class UserDto {


    private Long id;
    private List<Order> orders;
    private String username;
    private String password;
    private String password1;
}
