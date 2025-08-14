package com.example.demo;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class RSAUtil {
    // 私钥解密方法
    public static String decrypt(String encryptedData, String privateKeyStr) throws Exception {
        try {
            // 将Base64编码的私钥字符串转换为PrivateKey对象
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        
            // 初始化Cipher对象进行解密
            // 明确指定填充模式
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.err.println("解密失败: " + e.getMessage());
            e.printStackTrace();
            throw e; // 重新抛出异常，让调用者处理
        }
    }

    // 公钥加密方法，可用于测试
    public static String encrypt(String plainText, String publicKeyStr) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").trim());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // 明确指定填充模式
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static void testKeyPair(String publicKey, String privateKey) throws Exception {
        String testData = "123456";
        String encrypted = encrypt(testData, publicKey);
        String decrypted = decrypt(encrypted, privateKey);
        System.out.println("密钥对测试: " + (testData.equals(decrypted) ? "成功" : "失败"));
    }
}