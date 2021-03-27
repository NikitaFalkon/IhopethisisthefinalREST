package com;

import com.Model.User;
import com.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
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
    public boolean Update(User user, long id) {
        //Client client1 = clientRepository.findById(id).orElseThrow();
        User user1 = userRepository.findById(id).orElseThrow();
        if (user1!=null)
        {
            BeanUtils.copyProperties(user, user1, "id");
            //user1.setPassword(passwordEncoder.encode(client1.getPassword()));
            userRepository.save(user1);
            return true;
        }
        return false;
    }
    public User loadUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
}
