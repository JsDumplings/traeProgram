// 由于找不到 '@utils/request' 模块，需要检查文件路径是否正确。
// 这里假设该模块实际路径为 'src/utils/request'，可根据实际情况修改。
import request from '@/utils/request';
import { encryptData } from '@/utils/crypto';

// 删除硬编码的公钥
// const publicKey = `-----BEGIN PUBLIC KEY-----
// MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8HMr2CBpoZPm3t9tCVlrKtTmI4jNJc7/HhxjIEiDjC8czP4PV+44LjXvLYcSV0fwi6nE4LH2c5PBPEnPfqp0g8TZeX+bYGvd70cXee9d8wHgBqi4k0J0X33c0ZnW7JruftPyvJo9OelYSofBXQTcwI+3uIl/YvrgQRv6A5mW01QIDAQAB
// -----END PUBLIC KEY-----`

// 定义登录接口的请求参数类型
interface LoginParams {
  username: string;
  password: string;
}

// 定义获取后端公钥的函数
export const getPublicKey = async () => {
  return request.get('/public-key');
};

// 定义加密密码的函数
export const encryptPassword = async (password: string): Promise<string> => {
  try {
    // 获取公钥
    const publicKey = await getPublicKey();
    // 使用公钥加密密码
    const encryptedPassword = await encryptData(publicKey, password);
    if (!encryptedPassword) {
      throw new Error('密码加密失败');
    }
    return encryptedPassword;
  } catch (error) {
    console.error('加密密码失败:', error);
    throw error;
  }
};

// 修改登录接口，使用动态获取的公钥加密
export const login = async (params: LoginParams) => {
  // 使用动态获取的公钥加密密码
  const encryptedPassword = await encryptPassword(params.password);
  return request.post('/login', {
    username: params.username,
    password: encryptedPassword
  });
};


