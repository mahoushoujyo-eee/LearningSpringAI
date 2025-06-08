package com.atguigu.learningspringai;

import com.atguigu.learningspringai.mcp.MyMCPService;
import com.atguigu.learningspringai.rag.RagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class LearningSpringAiApplicationTests {


    @Autowired
    private MyMCPService myMCPService;

    @Autowired
    RagService ragService;

    @Test
    void contextLoads() throws IOException {
        ragService.addDocumentFromResources("sample.txt");
    }

    @Test
    void read()
    {
        ragService.query("那一天的忧郁");
    }

}
