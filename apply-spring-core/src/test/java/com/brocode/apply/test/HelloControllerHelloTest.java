package com.brocode.apply.test;

import com.brocode.apply.ApplyApplication;
import com.brocode.apply.HelloController;
import com.brocode.apply.buissness.model.Applier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

@SpringBootTest(classes = {ApplyApplication.class})
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@TestPropertySource("classpath:application-test.properties")
class HelloControllerHelloTest {
    @Autowired
    HelloController helloController;

    @Test
    void greetTheWorld() {
        Applier hello = helloController.hello();
        Assert.hasText(hello.getUsername(), "World");
    }
}