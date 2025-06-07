# Spring AI 学习项目

## 项目介绍

这是一个基于 Spring AI 框架开发的 Java 应用程序，用于学习和演示如何集成大型语言模型(LLM)到 Spring Boot 应用中。项目主要使用 Spring AI 与 OpenAI 兼容的 API 接口进行交互，实现了多种 AI 对话场景，包括普通对话、流式响应、角色扮演、函数调用以及对话记忆等功能。

## 技术栈

- Java 17
- Spring Boot 3.4.6
- Spring AI 1.0.0
- MySQL (用于对话记忆存储)
- MyBatis
- Redis

## 功能特性

- **基础对话**: 通过 ChatClient 实现与 LLM 的基本对话功能
- **流式响应**: 支持流式返回大模型回答，提升用户体验
- **角色扮演**: 通过角色提示词实现特定角色的对话场景
- **函数/工具调用**: 支持调用外部函数或服务，增强 AI 能力
- **对话记忆**: 基于 JDBC 的持久化对话记忆，支持多会话管理
- **多渠道配置**: 支持多种 AI 服务提供商的配置接入

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── atguigu/
│   │           └── learningspringai/
│   │               ├── LearningSpringAiApplication.java   # 应用程序入口
│   │               ├── advisor/                           # 对话顾问相关类
│   │               │   └── ChatMemoryAdvisor.java         # 对话记忆管理顾问
│   │               ├── config/                            # 配置类
│   │               │   ├── AIConfig.java                  # AI 基础配置
│   │               │   ├── ChatMemoryConfig.java          # 对话记忆配置
│   │               │   └── OneMCPConfig.java              # MCP配置
│   │               ├── context/                           # 上下文管理
│   │               │   ├── JdbcChatMemory.java            # JDBC对话记忆实现
│   │               │   ├── MyChatMemory.java              # 自定义对话记忆实现
│   │               │   └── RepositoryChatMemory.java      # 存储库对话记忆实现
│   │               ├── controller/                        # 控制器
│   │               │   ├── ChatController.java            # 基础对话控制器
│   │               │   ├── ChatMemoryController.java      # 对话记忆控制器
│   │               │   ├── ChatModelController.java       # 聊天模型控制器
│   │               │   ├── MCPController.java             # MCP控制器
│   │               │   ├── RoleChatController.java        # 角色对话控制器
│   │               │   └── ToolChatController.java        # 工具对话控制器
│   │               ├── entity/                            # 实体类
│   │               │   └── ChatMessage.java               # 聊天消息实体
│   │               ├── function/                          # 函数调用相关
│   │               │   └── FunctionCallingConfigure.java  # 函数调用配置
│   │               ├── mapper/                            # MyBatis Mapper
│   │               ├── mcp/                               # MCP 相关
│   │               │   └── MyMCPService.java              # MCP服务实现
│   │               └── tool/                              # 工具类
│   │                   └── ToolTest.java                  # 工具测试类
│   └── resources/
│       ├── application.properties                         # 应用配置
│       ├── mapper/                                        # MyBatis映射文件
│       │   └── ChatMessageMapper.xml                      # 聊天消息映射
│       └── mcp/
│           └── fetch-web.json                             # MCP配置文件
└── test/
    └── java/
        └── com/
            └── atguigu/
                └── learningspringai/
                    └── LearningSpringAiApplicationTests.java  # 测试类
```

## 快速开始

### 环境要求

- JDK 17 或更高版本
- Maven 3.8+
- MySQL 8.0+
- Redis (可选)

### 配置说明

1. 在 `application.properties` 中配置你的 AI 服务提供商信息：

```properties
# OpenAI 配置
spring.ai.openai.api-key=你的API密钥
spring.ai.openai.base-url=你的API地址
spring.ai.openai.chat.options.model=模型名称
spring.ai.openai.chat.options.temperature=0.7

# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/spring_ai?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=你的密码
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# MyBatis 配置
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.atguigu.learningspringai.entity
mybatis.configuration.map-underscore-to-camel-case=true
```

2. 创建 MySQL 数据库和必要的表结构

### 运行项目

1. 克隆代码库
2. 进入项目根目录
3. 执行 `mvn spring-boot:run`
4. 访问 `http://localhost:8899` 开始使用

## API 接口

- 基础对话：`/ai/chat`
- 流式响应：`/ai/stream`
- 角色对话：`/role-chat`
- 带记忆的对话：`/chat-memory/chat?conversationId={会话ID}`
- 函数调用：`/tool/chat`

## 注意事项

- 请确保已配置正确的 API 密钥和基础 URL
- 对话记忆功能需要正确配置数据库
- 流式响应接口需要支持服务端事件推送 (SSE)

## 参考资料

- [Spring AI 官方文档](https://docs.spring.io/spring-ai/reference/index.html)
- [Spring Boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/)
