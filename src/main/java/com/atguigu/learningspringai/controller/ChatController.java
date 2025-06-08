package com.atguigu.learningspringai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.DefaultChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Autowired
    private OpenAiChatModel chatModel;


    //这里能换个注入方式吗？怎么做？
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat-model/chat")
    public String chatModelTest(@RequestParam(value = "message", defaultValue = "hello")
                           String message)
    {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://api.qnaigc.com")
                .apiKey("sk-d1485a93f87c3c0add520ca9a97d507cb810537de27ac5d9a72f2b6ba4651a0d")
                .build();
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("deepseek-v3")
                .temperature(0.8)
                .build();
        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .defaultOptions(options)
                .openAiApi(openAiApi).build();


        String response = chatModel.call(message);
        System.out.println("response : "+response);
        return response;
    }

    @GetMapping("/ai/chat")
    public String chat(@RequestParam(value = "message",defaultValue = "给我讲个笑话")
                       String message) {
        //prompt:提示词
        return this.chatClient.prompt()
                //用户输入的信息
                .user(message)
                //请求大模型
                .call()
                //返回文本
                .content();
    }

    @GetMapping(value = "/ai/stream/chat", produces = "text/event-stream;charset=UTF-8")
    public Flux<String> streamChat(@RequestParam(value = "message",defaultValue = "给我讲个笑话")
                             String message) {
        //prompt:提示词
        return this.chatClient.prompt()
                //用户输入的信息
                .user(message)
                //请求大模型
                .stream()
                //返回文本
                .content();
    }

    @GetMapping("/chat-client/chat")
    public String chatClientTest(@RequestParam(value = "message", defaultValue = "hello")
                           String message) {
        ChatClient chatClient = ChatClient.builder(chatModel)
                .build();

        return chatClient.prompt()
                .user(message)
                //.toolCallbacks()
                //.advisors()
                //.system()
                .call()
                .content();
    }
}
