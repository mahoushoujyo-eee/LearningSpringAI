package com.atguigu.learningspringai.function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionCallingConfigure
{
    public record AddOperation(int a, int b) {

    }

    public record MulOperation(int m, int n) {

    }

    @Bean
    @Description("加法运算")
    public Function<AddOperation, Integer> addOperation() {
        return request -> {
            System.out.println("加法运算执行了");
            return request.a + request.b;
        };
    }

    @Bean
    @Description("乘法运算")
    public Function<MulOperation, Integer> mulOperation() {
        return request -> {
            System.out.println("乘法运算执行了");
            return request.m * request.n;
        };
    }
}
