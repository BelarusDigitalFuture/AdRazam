package com.adrazam.controller;

import com.adrazam.model.User;
import com.adrazam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/newAccount")
    public String newUserPost(@ModelAttribute("textFile") MultipartFile textFile,
                              @ModelAttribute("login") String login,
                              @ModelAttribute("password") String password,
                              @RequestParam("radioName") String student) {
        User user = userService.createUser(login, password, student);
        try {
            if (textFile != null) {
                byte[] bytes = textFile.getBytes();
                user.getUserInfo().setConfirmation(user.getId() + ".txt");
                userService.save(user);
                String name = user.getId() + ".png";
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/resources/static/image/user/" + name)));
                stream.write(bytes);
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "userProfile";
    }
}
