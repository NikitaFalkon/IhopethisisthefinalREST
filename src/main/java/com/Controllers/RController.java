package com.Controllers;

import com.Model.ListOfUsers;
import com.Model.User;
import com.Repository.UserRepository;
import com.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class RController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @GetMapping(value ="/user")
    public User User(@RequestParam("username") String name, @RequestParam("file") String file)
    {
        User user = userRepository.findByUsername(name);
        HttpEntity<String> entity = new HttpEntity<String>(file);
        ResponseEntity responce= restTemplate
                .exchange("http://localhost:8060//write/{filename}?text="+user.toString()+"",
                        HttpMethod.GET,
                        entity,
                        ResponseEntity.class,
                        file);
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
    @PutMapping(value ="/user/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id)
    {
        User user = userRepository.findById(id).orElseThrow();
        boolean update = userService.Update(user, id);
        return update
                ?new ResponseEntity<>(HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestParam("username") String name, @RequestParam("password") String password)
    {
        boolean create = userService.Create(name, password);
        return create
                ?new ResponseEntity<>(HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
