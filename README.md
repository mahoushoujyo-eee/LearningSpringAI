# Spring AI 学习项目

## 项目介绍

这是一个基于Spring AI框架开发的Java应用程序，用于学习和演示如何集成大型语言模型(LLM)到Spring Boot应用中。项目主要使用Spring AI与OpenAI兼容的API接口进行交互，实现了多种AI对话场景，包括普通对话、流式响应、角色扮演和函数调用等功能。

## 技术栈

- Java 17
- Spring Boot 3.5.0
- Spring AI 1.0.0-M5
- OpenAI兼容API

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── atguigu/
│   │           └── learningspringai/
│   │               ├── LearningSpringAiApplication.java   # 应用程序入口
│   │               ├── config/
│   │               │   └── AIConfig.java                  # AI配置类
│   │               ├── controller/
│   │               │   ├── ChatController.java            # 基础聊天控制器
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

## API接口说明

### 基础聊天接口

- **普通对话**: `/ai/chat/1?message=消息内容`
- **流式响应**: `/ai/stream/chat?message=消息内容`
- **模型直接调用**: `/ai/generate/1?message=消息内容`

### 高级聊天接口

- **角色扮演**: `/ai/role/chat/1?message=消息内容`
- **函数调用**: `/chat-model/function-calling?message=消息内容`
- **提示词模板**: `/chat-model/prompt?name=名称&voice=声音风格`

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

## 注意事项

- 项目使用的是OpenAI兼容API，当前配置使用的是通过`api.qnaigc.com`访问的`deepseek-v3`模型
- 请确保您的API密钥有效且有足够的调用额度
- 默认运行端口为8899，可在配置文件中修改

## 扩展开发

如需添加新的函数调用功能：
1. 在`FunctionCallingConfigure`类中添加新的record定义
2. 添加对应的`@Bean`方法
3. 在控制器中通过`functions()`方法调用新增的函数

## 开发者信息

本项目为Spring AI学习示例，基于尚硅谷教学内容开发。
