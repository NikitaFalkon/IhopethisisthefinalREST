package com.Controllers;

import com.Model.User;
import com.Security.JWT.JwtTokenProvider;
import com.UserService;
import com.dto.AuthenticationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController {
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password)
    //public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto)
    {
        /*String username = requestDto.getUsername();
        String password = requestDto.getPassword();*/
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userService.loadUserByUsername(username);


        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        return true
                ?new ResponseEntity<>(HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
