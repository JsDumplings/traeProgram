package com.example.demo;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    // 模拟数据库存储用户名和密码
    private static final Map<String, String> userDatabase = new HashMap<>();

    // 注册用户
    public static void registerUser(String username, String password) {
        userDatabase.put(username, password);
    }

    // 验证用户密码
    public static boolean verifyUser(String username, String decryptedPassword) {
        String storedPassword = userDatabase.get(username);
        return storedPassword != null && storedPassword.equals(decryptedPassword);
    }
}