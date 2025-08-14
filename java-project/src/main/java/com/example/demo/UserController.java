package com.example.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

private static final String PRIVATE_KEY = getPrivateKey(); 

// 假设这是获取私钥的方法，实际使用时需要根据具体情况实现
private static String getPrivateKey() {
    try {
        // 创建RSA密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为2048位
        keyPairGenerator.initialize(2048);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 将私钥编码为字节数组并进行Base64编码
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    } catch (NoSuchAlgorithmException e) {
        // 处理算法不存在的异常
        e.printStackTrace();
        return "";
    }
}

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String encryptedPassword) {
        try {
            // 解密前端传来的密码
            String decryptedPassword = RSAUtil.decrypt(encryptedPassword, PRIVATE_KEY);

            // 验证密码
            boolean isVerified = UserService.verifyUser(username, decryptedPassword);

            if (isVerified) {
                return "登录成功";
            } else {
                return "用户名或密码错误";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "登录失败，请稍后重试";
        }
    }
}