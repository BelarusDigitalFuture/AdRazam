package com.adrazam.service;

import com.adrazam.model.User;
import com.adrazam.model.UserInfo;
import com.adrazam.repository.RoleRepository;
import com.adrazam.repository.UserInfoRepository;
import com.adrazam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User createUser (String login, String password, String student) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        if(student.equals("student")) {
            user.setRole(roleRepository.getOne(1));
        }
        else {
            user.setRole(roleRepository.getOne(2));
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        save(user);
        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exist"));
        return User.fromUser(user);
    }
}
