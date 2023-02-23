package com.feira.joblist.service;

import com.feira.joblist.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface IUserService {
    User getByUsername(String username);
    void save(User user);
}
