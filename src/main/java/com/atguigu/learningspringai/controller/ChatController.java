package com.atguigu.learningspringai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

//    @Autowired
//    private OpenAiChatModel chatModel;
//
//
//    //这里能换个注入方式吗？怎么做？
//    private final ChatClient chatClient;
//
//    public ChatController(ChatClient.Builder chatClientBuilder) {
//        this.chatClient = chatClientBuilder.build();
//    }
//
//    @GetMapping("/ai/generate/1")
//    public String chatModelTest(@RequestParam(value = "message", defaultValue = "hello")
//                           String message)
//    {
//        String response = this.chatModel.call(message);
//        System.out.println("response : "+response);
//        return response;
//    }
//
//    @GetMapping("/ai/chat/1")
//    public String chat(@RequestParam(value = "message",defaultValue = "给我讲个笑话")
//                       String message) {
//        //prompt:提示词
//        return this.chatClient.prompt()
//                //用户输入的信息
//                .user(message)
//                //请求大模型
//                .call()
//                //返回文本
//                .content();
//    }
//
//    @GetMapping(value = "/ai/stream/chat", produces = "text/event-stream;charset=UTF-8")
//    public Flux<String> streamChat(@RequestParam(value = "message",defaultValue = "给我讲个笑话")
//                             String message) {
//        //prompt:提示词
//        return this.chatClient.prompt()
//                //用户输入的信息
//                .user(message)
//                //请求大模型
//                .stream()
//                //返回文本
//                .content();
//    }
}
