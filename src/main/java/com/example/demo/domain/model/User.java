package com.example.demo.domain.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

// TODO: 21.03.2021 formularz rejestracji uzykowika (get i post),
//  zabezpieczenie poprzez security-tylko niezalogowany moze,
//  haszowanie hasla, sprawdzanie czy jest taki user.
//  zaczac od url post i get - > do security -> moze tulko niezalogowany.
//  haslo mus byc wpisywane 2 razy + sprawdzanie czy 2 razy jest to samo.


@Entity
@Data
@Table(name="app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="user")
    private List<Order> orders;

    private String username;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
