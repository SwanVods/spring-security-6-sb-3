package com.feira.joblist.service;

import com.feira.joblist.model.User;
import com.feira.joblist.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo repo;

    public User getByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public void save(User user) {
        repo.save(user);
    }
}
