package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    private static final String PRIVATE_KEY = getPrivateKey();
    private static String PUBLIC_KEY; // 存储公钥

    // 生成密钥对
    private static String getPrivateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PUBLIC_KEY = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            return Base64.getEncoder().encodeToString(privateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 获取公钥的接口
    @GetMapping("/public-key")
    public String getPublicKey() {
        return "-----BEGIN PUBLIC KEY-----.\n" + PUBLIC_KEY + "\n-----END PUBLIC KEY-----";
    }

    // 登录接口
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        try {
            // 解密前端传来的密码
            String decryptedPassword = RSAUtil.decrypt(password, PRIVATE_KEY);
            System.out.println("decryptedPassword: " + decryptedPassword);

            boolean isVerified = userService.verifyUser(username, decryptedPassword);
            System.out.println("isVerified: " + isVerified);

            if (isVerified) {
                return "登录成功";
            } else {
                return "用户名或密码错误";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "登录失败: " + e.getMessage();
        }
    }

    // 注册接口
    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        try {
            // 解密前端传来的密码
            String decryptedPassword = RSAUtil.decrypt(password, PRIVATE_KEY);
            userService.registerUser(username, decryptedPassword);
            return "注册成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "注册失败: " + e.getMessage();
        }
    }
}