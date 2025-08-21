import JSEncrypt from 'jsencrypt';

const encryptInstance = new JSEncrypt();

// 加密函数
export function encryptData(publicKey: string, data: string): string {
  try {
    // 验证公钥格式
    if (!publicKey || !publicKey.includes('-----BEGIN PUBLIC KEY-----') || !publicKey.includes('-----END PUBLIC KEY-----')) {
      throw new Error('公钥格式不正确，请使用PEM格式的公钥');
    }
    encryptInstance.setPublicKey(publicKey);
    const encrypted = encryptInstance.encrypt(data);
    if (!encrypted) {
      throw new Error('加密失败: 可能是数据过长或公钥无效');
    }
    return encrypted;
  } catch (error) {
    console.error('加密过程中出现错误:', error);
    return '';
  }
}