package com.atguigu.learningspringai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
public class RAGController
{
    @Autowired
    private ChatClient chatClient;


    @RequestMapping("/chat")
    public String chat(@RequestParam(value = "message",defaultValue = "你是谁") String question)
    {
       return chatClient.prompt()
       .user(question)
       .call()
       .content();
    }
}
