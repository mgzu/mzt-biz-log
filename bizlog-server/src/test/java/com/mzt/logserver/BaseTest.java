package com.mzt.logserver;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = BaseTest.Application.class)
public abstract class BaseTest {

    @MapperScan(value = "com.mzt.logserver.repository.mapper", annotationClass = Mapper.class, lazyInitialization = "true")
    @ComponentScan(value = "com.mzt.logserver")
    public static class Application {

    }
}
