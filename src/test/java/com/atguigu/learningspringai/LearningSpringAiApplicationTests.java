package com.atguigu.learningspringai;

import com.atguigu.learningspringai.mcp.MyMCPService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearningSpringAiApplicationTests {


    @Autowired
    private MyMCPService myMCPService;

    @Test
    void contextLoads() {
    }

}
