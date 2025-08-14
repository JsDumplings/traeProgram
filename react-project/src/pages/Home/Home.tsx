import * as React from 'react';
import { Layout, Menu } from 'antd';
import { Outlet } from 'react-router-dom';
import './Home.scss';

const { Header, Sider, Content } = Layout;

const Home: React.FC = () => {
  const username = localStorage.getItem('username') || '未知用户';

  return (
    <Layout className="home-layout">
      <Header className="header">
        <div className="system-name">系统名称</div>
        <div className="username">{username}</div>
      </Header>
      <Layout>
        <Sider className="sider-menu">
          <Menu theme="dark" mode="inline" defaultSelectedKeys={['1']}>
            <Menu.Item key="1">菜单项1</Menu.Item>
            <Menu.Item key="2">菜单项2</Menu.Item>
          </Menu>
        </Sider>
        <Layout style={{ padding: '24px' }}>
          <Content className="content-area">
            <Outlet />
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
};

export default Home;