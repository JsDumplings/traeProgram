import * as React from 'react';
import { useState } from 'react';
import { Form, Input, Button, message, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import './Register.scss';

const { Title } = Typography;

const Register: React.FC = () => { 
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: { username: string; password: string }) => {
    setLoading(true);
    try {
      // 这里需要替换为实际的注册接口
      // const response = await register(values);
      message.success('注册成功，请登录');
      navigate('/login');
    } catch (error) {
      console.error('注册失败:', error);
      message.error('注册失败，请重试');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-container">
      <Title level={2} className="system-name">鹦鹉管理系统 - 注册</Title>
      <Form 
        onFinish={onFinish} 
        className="register-form"
        layout="vertical" 
        style={{ 
          maxWidth: 400, 
          margin: '0 auto' 
        }} 
      >
        <Form.Item
          name="username"
          rules={[
            { required: true, message: '请输入用户名', whitespace: true }, 
            { min: 3, message: '用户名至少3个字符' }, 
            { max: 20, message: '用户名最多20个字符' } 
          ]}
          label="用户名"
        >
          <Input placeholder="请输入用户名" />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            { required: true, message: '请输入密码', whitespace: true }, 
            { min: 6, message: '密码至少6个字符' }, 
            { max: 32, message: '密码最多32个字符' } 
          ]}
          label="密码"
        >
          <Input.Password placeholder="请输入密码" />
        </Form.Item>
        <Form.Item
          name="confirmPassword"
          dependencies={['password']}
          rules={[
            { required: true, message: '请确认密码' },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue('password') === value) {
                  return Promise.resolve();
                }
                return Promise.reject(new Error('两次输入的密码不一致'));
              },
            }),
          ]}
          label="确认密码"
        >
          <Input.Password placeholder="请再次输入密码" />
        </Form.Item>
        <Form.Item>
          <Button 
            type="primary" 
            htmlType="submit" 
            loading={loading} 
            style={{ 
              width: '100%',
              height: 40, 
              fontSize: 16 
            }}
          >
            注册
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default Register;