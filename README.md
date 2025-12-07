# 1inorder 微信小程序

![GitHub Stars](https://img.shields.io/github/stars/huey1in/1inorder_back)
![GitHub Forks](https://img.shields.io/github/forks/huey1in/1inorder_back)
![UniApp](https://img.shields.io/badge/UniApp-3.x-blue)
![Vue](https://img.shields.io/badge/Vue-3.x-green)
![WeChat MiniApp](https://img.shields.io/badge/WeChat-MiniApp-09B726)

基于 UniApp + Vue 3 开发的微信小程序。

## 项目结构

```
font/
├── pages/                              # 页面文件
│   ├── index/
│   │   └── index.uvue                  # 首页
│   ├── login/
│   │   └── login.uvue                  # 登录页
│   ├── register/
│   │   └── register.uvue               # 注册页
│   ├── product/
│   │   └── detail.uvue                 # 产品详情
│   ├── cart/
│   │   └── cart.uvue                   # 购物车
│   ├── order/
│   │   ├── create.uvue                 # 创建订单
│   │   ├── list.uvue                   # 订单列表
│   │   └── detail.uvue                 # 订单详情
│   ├── address/
│   │   ├── list.uvue                   # 地址列表
│   │   └── edit.uvue                   # 编辑地址
│   ├── my/
│   │   └── my.uvue                     # 个人中心
│   ├── settings/
│   │   ├── settings.uvue               # 设置页
│   │   ├── profile.uvue                # 个人资料
│   │   └── password.uvue               # 修改密码
│   └── legal/
│       ├── agreement.uvue              # 用户协议
│       └── privacy.uvue                # 隐私政策
├── common/
│   └── config.uts                      # 全局配置
├── static/
│   ├── icons/                          # 图标资源
│   └── tabbar/                         # 底部导航资源
├── App.uvue                            # 根组件
├── main.uts                            # 应用入口
├── pages.json                          # 页面配置
├── manifest.json                       # 应用清单
├── uni.scss                            # 全局样式
├── .env.example                        # 环境变量模板
├── .gitignore                          # Git 忽略文件
└── package.json
```

## 快速开始
### 环境要求
- Node.js 16+
- npm 或 yarn
- HBuilderX（推荐）- 用于微信小程序开发
- 微信开发者工具

### 安装依赖
```bash
cd font
npm install
```

### 配置环境变量
```bash
# 复制模板文件
cp .env.example .env

# 编辑 .env 文件，配置：
# VITE_API_BASE_URL=http://localhost:3000/api
```

### 开发和调试

#### 方式一：使用 HBuilderX（推荐）
1. 在 HBuilderX 中打开 font 目录
2. 点击菜单 `运行 > 运行到小程序模拟器 > 微信开发者工具`
3. 自动打开微信开发者工具进行调试

#### 方式二：命令行编译
```bash
# 编译到微信小程序
npm run build:mp-weixin

# 然后用微信开发者工具打开 dist/dev/mp-weixin 目录
```

### 预览和发布

1. 在微信开发者工具中测试功能
2. 编译生产版本后上传至微信公众平台
3. 提交审核并发布 run build:app
```
## 主要技术

- **框架**: UniApp（Vue 3）
- **语言**: UTS（UniApp TypeScript）
- **小程序平台**: 微信小程序
- **UI 组件**: uni-ui
- **HTTP 请求**: uni.request
- **本地存储**: uni.storage
- **API 通信**: REST API + JWT 认证
- **存储**: uni.storage
- **平台支持**: H5、App（iOS/Android）、小程序

## 页面说明

### 首页 (index)
- 产品分类展示
- 热销产品列表
- 搜索功能
- 购物车快捷入口

### 登录/注册 (login/register)
- 手机号登录
- 密码注册
- 验证码验证
- 记住登录状态

### 产品详情 (product/detail)
- 产品图片展示
- 产品详细信息
- 价格和库存显示
- 添加到购物车

### 购物车 (cart)
- 购物车商品列表
- 数量修改
- 价格计算
- 清空购物车
- 结算功能

### 订单 (order)
- 订单创建流程
- 订单列表查看
- 订单详情和追踪
- 订单状态管理

### 地址管理 (address)
- 地址列表
- 新增地址
- 编辑地址
- 默认地址设置

### 个人中心 (my)
- 用户信息展示
- 订单历史
## 开发规范

- 使用 `.uvue` 单文件组件
- UTS 类型注解
- 样式使用 `scoped`
- 小程序样式兼容性考虑
- 遵循 UniApp 官方最佳实践
- 微信小程序特有 API 使用


## 许可证

MIT License

Copyright (c) 2025 huey1in

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## 作者

1in
