package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 注入UserRepository
    @Autowired
    private UserRepository userRepository;

    // 注册用户
    public void registerUser(String username, String password) {
        User user = new User(username, password);
        userRepository.save(user);
        System.out.println("用户注册成功: " + username);
    }

    // 验证用户密码
    public boolean verifyUser(String username, String decryptedPassword) {
        User user = userRepository.findByUsername(username);
        System.out.println("查询到用户: " + (user != null ? user.getUsername() : "不存在"));

        if (user != null) {
            boolean isMatch = user.getPassword().equals(decryptedPassword);
            System.out.println("密码验证: " + (isMatch ? "成功" : "失败"));
            return isMatch;
        }
        return false;
    }
}