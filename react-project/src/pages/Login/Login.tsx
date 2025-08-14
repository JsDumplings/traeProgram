import * as React from 'react';
import { useState } from 'react';
import { Form, Input, Button, message, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
// 尝试直接引入文件，若文件确实存在，可能需要检查路径映射配置
// 假设 api/login 文件的实际相对路径是 '../../api/login'，可根据实际情况调整
import { login } from '../../api/login';
import './Login.scss';
import { Link } from 'react-router-dom';

const { Title } = Typography;

const Login: React.FC = () => { 
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: { username: string; password: string }) => {
    setLoading(true);
    try {
      const response = await login(values);
      if (response.data && response.data.token) {
        localStorage.setItem('token', response.data.token);
      }
      navigate('/');
      message.success('登录成功');
    } catch (error) {
      console.error('登录失败:', error);
      message.error('登录失败，请检查用户名和密码');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <Title level={2} className="system-name">鹦鹉管理系统</Title>
      <Form 
        onFinish={onFinish} 
        className="login-form"
        layout="vertical" // 垂直布局，使标签显示在输入框上方
        style={{ 
          maxWidth: 400, // 原300px，增加100px后改为400px
          margin: '0 auto' 
        }} // 限制表单宽度并居中
      >
        <Form.Item
          name="username"
          rules={[
            { required: true, message: '请输入用户名', whitespace: true }, // 增加去除空格校验
            { min: 3, message: '用户名至少3个字符' }, // 增加最小长度校验
            { max: 20, message: '用户名最多20个字符' } // 增加最大长度校验
          ]}
          label="用户名"
        >
          <Input placeholder="请输入用户名" />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            { required: true, message: '请输入密码', whitespace: true }, // 增加去除空格校验
            { min: 6, message: '密码至少6个字符' }, // 增加最小长度校验
            { max: 32, message: '密码最多32个字符' } // 增加最大长度校验
          ]}
          label="密码"
        >
          <Input.Password placeholder="请输入密码" />
        </Form.Item>
        <Form.Item>
          <Button 
            type="primary" 
            htmlType="submit" 
            loading={loading} 
            style={{ 
              width: '100%',
              height: 40, // 增加按钮高度
              fontSize: 16 // 增加按钮字体大小
            }}
          >
            登录
          </Button>
        </Form.Item>
      </Form>
      <div style={{ textAlign: 'center', marginTop: '16px' }}>
        还没有账号？<Link to="/register">立即注册</Link>
      </div>
    </div>
  );
};

export default Login;