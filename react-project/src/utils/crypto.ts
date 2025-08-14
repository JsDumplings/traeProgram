import JSEncrypt from 'jsencrypt';

const encryptInstance = new JSEncrypt();
const decryptInstance = new JSEncrypt();

// 加密函数
export function encryptData(publicKey: string, data: string): string { 
  try {
    encryptInstance.setPublicKey(publicKey);
    const encrypted = encryptInstance.encrypt(data);
    if (!encrypted) {
      throw new Error('加密失败');
    }
    return encrypted;
  } catch (error) {
    console.error('加密过程中出现错误:', error);
    return '';
  }
}

// 解密函数
export function decryptData(privateKey: string, encryptedData: string): string { 
  try {
    decryptInstance.setPrivateKey(privateKey);
    const decrypted = decryptInstance.decrypt(encryptedData);
    if (!decrypted) {
      throw new Error('解密失败');
    }
    return decrypted;
  } catch (error) {
    console.error('解密过程中出现错误:', error);
    return '';
  }
}