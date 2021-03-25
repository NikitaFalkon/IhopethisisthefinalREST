package com.Controllers;

import com.Model.ListOfUsers;
import com.Model.User;
import com.Repository.UserRepository;
import com.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @GetMapping(value ="/user")
    public User User(@RequestParam("username") String name)
    {
        User user = userRepository.findByUsername(name);
        return user;
    }
    @GetMapping(value = "/users")
    public ListOfUsers Users()
    {
        ListOfUsers listOfUsers = new ListOfUsers();
        listOfUsers.setUserList(userRepository.findAll());
        return listOfUsers;
    }
    @DeleteMapping(value ="/user/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        User user = userRepository.findById(id).orElseThrow();
        return userService.Delete(id)
                ?new ResponseEntity<>(HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
