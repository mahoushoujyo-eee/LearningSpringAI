# Spring AI 学习项目

## 项目介绍

这是一个基于Spring AI框架开发的Java应用程序，用于学习和演示如何集成大型语言模型(LLM)到Spring Boot应用中。项目主要使用Spring AI与OpenAI兼容的API接口进行交互，实现了多种AI对话场景，包括普通对话、流式响应、角色扮演、函数调用以及对话记忆等功能。

##目前官方文档和Maven仓库的依赖都不够完善，很多内容都直接没有任何资料，建议保持观望

## 技术栈

- Java 17
- Spring Boot 3.5.0
- Spring AI 1.0.0-M5
- OpenAI兼容API
- Spring JDBC (用于对话记忆存储)

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── atguigu/
│   │           └── learningspringai/
│   │               ├── LearningSpringAiApplication.java   # 应用程序入口
│   │               ├── advisor/
│   │               │   └── ChatMemoryAdvisor.java         # 对话记忆管理顾问
│   │               ├── config/
│   │               │   ├── AIConfig.java                  # AI配置类
│   │               │   └── ChatMemoryConfig.java          # 持久对话存储配置
│   │               ├── context/
│   │               │   ├── JdbcChatMemory.java            # JDBC对话记忆实现
│   │               │   └── MyChatMemory.java              # 自定义对话记忆实现
│   │               ├── controller/
│   │               │   ├── ChatController.java            # 基础聊天控制器
│   │               │   ├── ChatMemoryController.java      # 对话记忆控制器
│   │               │   ├── ChatModelController.java       # 更高级的聊天模型控制器
│   │               │   └── RoleChatController.java        # 角色扮演聊天控制器
│   │               └── function/
│   │                   └── FunctionCallingConfigure.java  # 函数调用配置
│   └── resources/
│       └── application.properties                         # 应用配置文件
```

## 功能特性

1. **基础聊天功能**
   - 普通对话响应
   - 流式响应输出

2. **高级聊天功能**
   - 模型参数自定义配置
   - 提示词模板使用
   - 系统消息定制

3. **函数调用功能**
   - 加法运算函数
   - 乘法运算函数

4. **角色扮演**
   - 自定义AI角色

5. **对话记忆功能**
   - 基于JDBC的对话历史存储
   - 自定义对话记忆实现

## API接口说明

### 基础聊天接口

- **普通对话**: `/ai/chat/1?message=消息内容`
- **流式响应**: `/ai/stream/chat?message=消息内容`
- **模型直接调用**: `/ai/generate/1?message=消息内容`

### 高级聊天接口

- **角色扮演**: `/ai/role/chat/1?message=消息内容`
- **函数调用**: `/chat-model/function-calling?message=消息内容`
- **提示词模板**: `/chat-model/prompt?name=名称&voice=声音风格`

### 对话记忆接口

- **带记忆的对话**: `/chat-memory/chat?message=消息内容&userId=用户ID`

### 其他接口

- **自定义模型调用**: `/chat-model/chat/1?message=消息内容`

## 配置说明

项目配置文件`application.properties`中包含以下主要配置：

```properties
server.port=8899

# API密钥和基础URL配置
spring.ai.openai.api-key=您的API密钥
spring.ai.openai.base-url=https://api.qnaigc.com

# 模型和参数配置
spring.ai.openai.chat.options.model=deepseek-v3
spring.ai.openai.chat.options.temperature=0.7

# 防止Spring Boot 3.4+ HTTP客户端工厂bug影响AI功能
spring.http.client.factory=jdk
```

## 如何启动

1. 确保已安装Java 17或以上版本
2. 克隆项目到本地
3. 修改`application.properties`文件中的API密钥为您自己的密钥
4. 使用以下命令运行项目：

```bash
./mvnw spring-boot:run
```

或者在IDE中直接运行`LearningSpringAiApplication.java`

## 使用示例

### 基础聊天
```
GET http://localhost:8899/ai/chat/1?message=给我讲个笑话
```

### 函数调用
```
GET http://localhost:8899/chat-model/function-calling?message=计算3加5等于多少
```

### 提示词模板
```
GET http://localhost:8899/chat-model/prompt?name=美食专家&voice=专业
```

### 对话记忆
```
GET http://localhost:8899/chat-memory/chat?message=你好&userId=user123
GET http://localhost:8899/chat-memory/chat?message=我们刚才聊了什么&userId=user123
```

## 开发说明

### 对话记忆功能

项目中的对话记忆功能允许AI模型记住与特定用户的对话历史。目前有两种实现：

1. `JdbcChatMemory` - 基于JDBC的实现，可以将对话记录存储在数据库中
2. `MyChatMemory` - 自定义实现，可以根据需要扩展
这里有几个核心常量，官方文档里没有介绍，这里给出：
```java
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
```
### 函数调用配置

函数调用允许AI模型调用预定义的Java函数。在`FunctionCallingConfigure.java`中定义了两个示例函数：

1. `addOperation` - 执行加法运算
2. `mulOperation` - 执行乘法运算

要添加新的函数：
1. 在`FunctionCallingConfigure`类中定义新的record类型
2. 添加带有`@Bean`和`@Description`注解的函数实现
3. 在控制器中通过`.functions()`方法调用新添加的函数

## 注意事项

- 项目使用的是OpenAI兼容API，当前配置使用的是通过`api.qnaigc.com`访问的`deepseek-v3`模型
- 请确保您的API密钥有效且有足够的调用额度
- 默认运行端口为8899，可在配置文件中修改
- 由于Spring Boot 3.4中的bug，添加了`spring.http.client.factory=jdk`配置，以避免某些AI功能（如ImageModel）出现问题

## 扩展开发

### 对话记忆存储扩展
要实现自定义的对话记忆存储，可以：
1. 实现`ChatMemory`接口
2. 覆盖必要的方法，如`add()`、`get()`和`clear()`
3. 通过Spring配置使其可用于注入

### 模型切换
项目支持切换不同的AI模型，只需在配置中修改：
```properties
spring.ai.openai.chat.options.model=您选择的模型
```

或在代码中通过`OpenAiChatOptions`动态指定。