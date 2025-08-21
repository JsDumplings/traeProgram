package com.example.demo;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class RSAUtil {
    // 存储公钥
    public static PublicKey PUBLIC_KEY;
    // 存储私钥
    public static PrivateKey PRIVATE_KEY;
    // 生成密钥对
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        System.out.println("publicKey:" + Base64.getEncoder().encodeToString(
                keyPairGenerator.generateKeyPair().getPublic().getEncoded()));
        System.out.println("privateKey:" + Base64.getEncoder().encodeToString(
                keyPairGenerator.generateKeyPair().getPrivate().getEncoded()));
        return keyPairGenerator.generateKeyPair();
    }
    // 私钥解密方法
    public static String decrypt(String encryptedData, PrivateKey privateKeyStr) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKeyStr);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, "UTF-8");
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
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 格式化Base64字符串（每64字符换行）
    public static String formatBase64WithNewlines(String base64) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < base64.length(); i += 64) {
            int end = Math.min(i + 64, base64.length());
            formatted.append(base64.substring(i, end));
            if (end < base64.length()) {
                formatted.append("\n");
            }
        }
        return formatted.toString();
    }
}