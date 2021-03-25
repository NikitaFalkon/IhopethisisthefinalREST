package com.Controllers;

import com.Model.User;
import com.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RController {
    @Autowired
    UserRepository userRepository;
    @GetMapping(value ="/user")
    public User Users(@RequestParam("username") String name)
    {
        User user = userRepository.findByUsername(name);
        return user;
    }
}
