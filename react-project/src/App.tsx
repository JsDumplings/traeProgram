import './App.css'
import * as React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import type { RouteObject } from 'react-router-dom';
import { routes } from './router';
import { Result, Button } from 'antd';

// 错误边界组件
// 为 state 定义明确的类型，解决属性不存在的问题
interface ErrorBoundaryState {
  hasError: boolean;
}

class ErrorBoundary extends React.Component<any, ErrorBoundaryState> {
  constructor(props: any) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error: Error) {
    console.error('捕获到错误:', error);
    // 更新 state 使下一次渲染能够显示降级后的 UI
    return { hasError: true };
  }

  componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
    console.error('错误信息:', errorInfo);
  }

  render() {
    if (this.state.hasError) {
      // 你可以自定义降级后的 UI 并渲染
      return (
        <Result
          status="500"
          title="500"
          subTitle="抱歉，发生了一些错误。"
          extra={<Button type="primary">返回首页</Button>}
        />
      );
    }

    return this.props.children; 
  }
}

const App = () => {
  return (
    <ErrorBoundary>
      <Router>
        <Routes>
          {/* 遍历路由配置并渲染 Route 组件 */}
          {routes.map((route: RouteObject, index: number) => {
            if (React.isValidElement(route.element)) {
              return <Route key={index} path={route.path} element={route.element} />;
            }
            // 输出更详细的错误信息
            console.error(`无效的路由元素，路由信息:`, route);
            return null;
          })}
        </Routes>
      </Router>
    </ErrorBoundary>
  );
};

export default App;
