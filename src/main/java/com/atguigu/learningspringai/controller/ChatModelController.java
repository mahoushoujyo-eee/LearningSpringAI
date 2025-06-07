package com.atguigu.learningspringai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/chat-model")
@RestController
public class ChatModelController
{
    @Autowired
    private ChatModel chatModel;

    @RequestMapping("/chat/1")
    public String chat(@RequestParam(value = "message",defaultValue = "你是谁")
                       String message)
    {
        ChatResponse response = chatModel.call(new Prompt(
                message,
                OpenAiChatOptions.builder()
                        //可以更换成其他大模型，如Anthropic3ChatOptions亚马逊
                        .model("deepseek-v3")
                        .temperature(0.8)
                        .build()
        ));

        return response.getResult().getOutput().getText();
    }

    @GetMapping("/function-calling")
    public String functionCalling(@RequestParam("message")
                                  String message){
        return ChatClient.builder(chatModel).build().prompt()
                .system("你是一个计算专家，可以帮助人们进行计算。")
                .user(message)
                //函数调用处，不过这个方法好像目前被废弃
                //.functions("addOperation", "mulOperation")
                .call()
                .content();
    }

    @GetMapping("/prompt")
    public String prompt(@RequestParam("name")
                         String name,
                         @RequestParam("voice")
                         String voice){
        String userText= """
            给我推荐北京的至少三种美食
            """;
        UserMessage userMessage = new UserMessage(userText);
        String systemText= """
            你是一个美食咨询助手，可以帮助人们查询美食信息。
            你的名字是{name},
            你应该用你的名字和{voice}的饮食习惯回复用户的请求。
            """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        //替换占位符
        Message systemMessage = systemPromptTemplate
                .createMessage(Map.of("name", name, "voice", voice));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        List<Generation> results = chatModel.call(prompt).getResults();
        return results.stream().map(x->x.getOutput().getText()).collect(Collectors.joining(""));
    }
}
