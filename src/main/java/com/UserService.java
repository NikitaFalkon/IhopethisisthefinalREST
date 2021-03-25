package com;

import com.Model.User;
import com.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
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
}
