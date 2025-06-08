# Spring AI 学习项目

## 项目介绍

这是一个基于 Spring AI 框架开发的 Java 应用程序，用于学习和演示如何集成大型语言模型(LLM)到 Spring Boot 应用中。项目主要使用 Spring AI 与 OpenAI 兼容的 API 接口进行交互，实现了多种 AI 对话场景，包括普通对话、流式响应、角色扮演、函数调用以及对话记忆等功能。同时项目使用了阿里云百炼的Embedding模型。
这里面是各种SpringAI框架的使用示例，建议搭配飞书介绍文档：[SpringAI 上手开发(https://ycn0a41n7m9n.feishu.cn/wiki/NgNFwxXUsih4uPktoRVc2CZ2nSf)，进行学习。

## 技术栈

- Java 17
- Spring Boot 3.4.6
- Spring AI 1.0.0
- MySQL (用于对话记忆存储)
- MyBatis
- ChromaDB

## 功能特性

- **基础对话**: 通过 ChatClient 实现与 LLM 的基本对话功能
- **流式响应**: 支持流式返回大模型回答，提升用户体验
- **角色扮演**: 通过角色提示词实现特定角色的对话场景
- **函数/工具调用**: 支持调用外部函数或服务，增强 AI 能力
- **对话记忆**: 基于 JDBC 的持久化对话记忆，支持多会话管理
- **多渠道配置**: 支持多种 AI 服务提供商的配置接入
- **RAG检索增强**: 通过向量存储实现问答检索增强生成
- **MCP多渠道处理**: 支持多渠道数据处理和集成

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
│   │               │   ├── EmbeddingConfig.java           # 嵌入向量配置
│   │               │   ├── OneMCPConfig.java              # MCP配置
│   │               │   └── TextSplitterConfig.java        # 文本分割器配置
│   │               ├── context/                           # 上下文管理
│   │               │   ├── JdbcChatMemory.java            # JDBC对话记忆实现
│   │               │   ├── MyChatMemory.java              # 自定义对话记忆实现
│   │               │   └── RepositoryChatMemory.java      # 存储库对话记忆实现
│   │               ├── controller/                        # 控制器
│   │               │   ├── ChatController.java            # 基础对话控制器
│   │               │   ├── ChatMemoryController.java      # 对话记忆控制器
│   │               │   ├── ChatModelController.java       # 聊天模型控制器
│   │               │   ├── MCPController.java             # MCP控制器
│   │               │   ├── RAGController.java             # RAG检索增强控制器
│   │               │   ├── RoleChatController.java        # 角色对话控制器
│   │               │   └── ToolChatController.java        # 工具对话控制器
│   │               ├── entity/                            # 实体类
│   │               │   └── ChatMessage.java               # 聊天消息实体
│   │               ├── function/                          # 函数调用相关
│   │               │   └── FunctionCallingConfigure.java  # 函数调用配置
│   │               ├── mapper/                            # MyBatis Mapper
│   │               │   └── ChatMessageMapper.java         # 聊天消息映射接口
│   │               ├── mcp/                               # MCP 相关
│   │               │   └── MyMCPService.java              # MCP服务实现
│   │               ├── rag/                               # RAG检索增强生成
│   │               │   └── RagService.java                # RAG服务实现
│   │               └── tool/                              # 工具类
│   │                   └── ToolTest.java                  # 工具测试类
│   └── resources/
│       ├── application.properties                         # 应用配置
│       ├── docs/                                          # 文档目录
│       │   ├── sample.txt                                 # 样本文本文件
│       │   └── pdf/                                       # PDF文档
│       │       └── springai.pdf                           # Spring AI文档
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

- JDK 17 
- Maven 3.8+
- MySQL 8.0+
- ChromaDB 1.0.13

### 配置说明

1. 在 `application.properties` 中配置你的 AI 服务提供商信息：

```properties
spring.application.name=LearningSpringAI

server.port=8899

spring.ai.openai.api-key=sk-xxx
spring.ai.openai.base-url=https://api.xxx.com
spring.ai.openai.chat.options.model=deepseek-v3
spring.ai.openai.chat.options.temperature=0.7

spring.ai.chat.memory.repository.jdbc.initialize-schema=never
#spring.ai.chat.memory.repository.jdbc.schema=classpath:chatmemory/schema.sql


#springai alibaba config
#spring.ai.dashscope.api-key=sk-a4d9b8b15f6a494b92491f3b6f2129f2

spring.datasource.url=jdbc:mysql://localhost:3306/spring_ai?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# MyBatis
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.atguigu.learningspringai.entity
mybatis.configuration.map-underscore-to-camel-case=true

#mcp
#spring.ai.mcp.client.sse.connections.fetch.url=https://mcp.api-inference.modelscope.net/a30b4276b9cc40
spring.ai.mcp.client.request-timeout=60s
spring.ai.mcp.client.type=ASYNC

#spring.ai.mcp.client.stdio.servers-configuration=classpath:mcp/fetch-web.json

#spring.ai.mcp.client.enabled=true
#spring.ai.mcp.client.name=my-mcp-client
#spring.ai.mcp.client.version=1.0.0
#spring.ai.mcp.client.request-timeout=30s
#spring.ai.mcp.client.type=SYNC
#spring.ai.mcp.client.sse.connections.server1.url=http://localhost:8080
#spring.ai.mcp.client.sse.connections.server2.url=http://otherserver:8081
#spring.ai.mcp.client.stdio.root-change-notification=false
#spring.ai.mcp.client.stdio.connections.server1.command=/path/to/server
#spring.ai.mcp.client.stdio.connections.server1.args[0]=--port=8080
#spring.ai.mcp.client.stdio.connections.server1.args[1]=--mode=production
#spring.ai.mcp.client.stdio.connections.server1.env.API_KEY=your-api-key
#spring.ai.mcp.client.stdio.connections.server1.env.DEBUG=true

spring.ai.vectorstore.chroma.client.host=http://localhost
spring.ai.vectorstore.chroma.client.port=58000
spring.ai.vectorstore.chroma.initialize-schema=true
spring.ai.vectorstore.chroma.collection-name=my-collection
```

2. 创建 MySQL 数据库和必要的表结构

```MySQL
CREATE TABLE `SPRING_AI_CHAT_MEMORY` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `conversation_id` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=470 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

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
- RAG检索增强：`/rag/chat`
- MCP多渠道处理：`/mcp/chat`

## 高级功能

### RAG检索增强生成

本项目实现了基于向量数据库的检索增强生成(RAG)功能，可以从文档中检索相关信息来增强AI的回答。项目支持处理文本和PDF文档，并使用嵌入模型将文档转换为向量存储。

### 对话记忆管理

提供了多种对话记忆实现方式：
- 基于JDBC的持久化存储
- 自定义内存存储
- 基于Repository的存储

### 函数调用能力

通过Spring AI的函数调用接口，实现了让AI模型能够调用外部函数和服务的能力，支持复杂的计算和数据处理任务。

## 注意事项

- 请确保已配置正确的 API 密钥和基础 URL
- 对话记忆功能需要正确配置数据库
- 流式响应接口需要支持服务端事件推送 (SSE)
- RAG功能需要预先导入文档到向量存储

## 参考资料

- [Spring AI 官方文档](https://docs.spring.io/spring-ai/reference/index.html)
- [Spring Boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/)
