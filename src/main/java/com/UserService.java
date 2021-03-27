package com;

import com.Model.User;
import com.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    private long CLIENT_ID_HOLDER;
    public boolean Delete(Long id)
    {
        User user = userRepository.findById(id).orElseThrow();
        if(user != null)
        {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
    public boolean Update(User user, long id) {
        User user1 = userRepository.findById(id).orElseThrow();
        if (user1!=null)
        {
            BeanUtils.copyProperties(user, user1, "id");
            user1.setPassword(passwordEncoder.encode(user1.getPassword()));
            userRepository.save(user1);
            return true;
        }
        return false;
    }
    public boolean Create(String username, String password) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        final long clientId = ++CLIENT_ID_HOLDER;
        user.setId(clientId);
        userRepository.save(user);
        return true;
    }

    public User loadUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
}
