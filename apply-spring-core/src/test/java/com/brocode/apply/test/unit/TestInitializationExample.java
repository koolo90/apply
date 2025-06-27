package com.brocode.apply.test.unit;

import org.junit.jupiter.api.*;

import java.text.MessageFormat;

/**
 * https://www.w3schools.com/java/ref_string_format.asp
 */
class TestInitializationExample {
    static int mainClassInitializtionCounter = 0;
    static int mainTestInitializationCounter = 0;
    static int mainTestExecutionCounter = 0;
    static String versionFormat = "Initialization: {0}.{1}.{2}";

    static {
        mainClassInitializtionCounter++;
        System.out.println("Static initializer - start");
        System.out.println(MessageFormat.format(versionFormat, mainTestExecutionCounter, mainTestInitializationCounter, mainClassInitializtionCounter));
        System.out.println("Static initializer - end");
    }

    @BeforeAll
    static void beforeAll() {
        mainClassInitializtionCounter++;
        System.out.println("BeforeAll initializer - start");
        System.out.println(MessageFormat.format(versionFormat, mainTestExecutionCounter, mainTestInitializationCounter, mainClassInitializtionCounter));
        System.out.println("BeforeAll initializer - end");
    }

    @AfterAll
    static void afterAll() {
        mainClassInitializtionCounter++;
        System.out.println("AfterAll tear down - start");
        System.out.println(MessageFormat.format("Tear down: {0}.{1}.{2}",
                mainTestExecutionCounter,
                mainTestInitializationCounter,
                mainClassInitializtionCounter));
        System.out.println("AfterAll tear down - end");
    }

    {
        mainTestInitializationCounter++;
        System.out.println("Instance initializer - start");
        System.out.println(MessageFormat.format(versionFormat, mainTestExecutionCounter, mainTestInitializationCounter, mainClassInitializtionCounter));
        System.out.println("Instance initializer - end");
    }

    @BeforeEach
    void beforeEach() {
        mainTestInitializationCounter++;
        System.out.println("BeforeEach initializer - start");
        System.out.println(MessageFormat.format(versionFormat, mainTestExecutionCounter, mainTestInitializationCounter, mainClassInitializtionCounter));
        System.out.println("BeforeEach initializer - end");
    }

    @AfterEach
    void afterEach() {
        mainTestInitializationCounter++;
        System.out.println("AfterEach tear down - start");
        System.out.println(MessageFormat.format("Tear down: {0}.{1}.{2}",
                mainTestExecutionCounter,
                mainTestInitializationCounter,
                mainClassInitializtionCounter));
        System.out.println("AfterEach tear down - end");
    }

    @Test
    void test1() {
        mainTestExecutionCounter++;
        System.out.println("Test [1] execution - start");
        System.out.println(MessageFormat.format(versionFormat, mainTestExecutionCounter, mainTestInitializationCounter, mainClassInitializtionCounter));
        System.out.println("Test [1] execution - end");

        /*String stringConcatenation = "Test: " + test + " " + value + " " + valueDec;
        String stringBuilderAppending = new StringBuilder()
                .append("Test: ")
                .append(test)
                .append(" ")
                .append(value)
                .append(" ")
                .append(valueDec).toString();
        String stringFormattedMethod = "Test: %s %d %s".formatted(test, value, valueDec);
        String messageFormatFormatMethod = MessageFormat.format("Test: {0} {1} {2}", test, value, valueDec);

        System.out.println("StringConcatenation value: " + stringConcatenation);
        System.out.println("StringBuilderAppending value: " + stringBuilderAppending);
        System.out.println("StringFormattedMethod value: " + stringFormattedMethod);
        System.out.println("MessageFormatFormatMethod value: " + messageFormatFormatMethod);*/
        /*Assertions.assertThat(concat).contains("field");
        Assertions.assertThat(concat).contains("static");
        Assertions.assertThat(concat).contains("BeforeClass");
        Assertions.assertThat(concat).contains("BeforeTest");
        Assertions.assertThat(concat).contains("Test1");*/
    }

    @Test
    void test2() {
        mainTestExecutionCounter++;
        System.out.println("Test [2] execution - start");
        System.out.println(MessageFormat.format(versionFormat, mainTestExecutionCounter, mainTestInitializationCounter, mainClassInitializtionCounter));
        System.out.println("Test [2] execution - end");

        /*String stringConcatenation = "Test: " + test + " " + value + " " + valueDec;
        String stringBuilderAppending = new StringBuilder()
                .append("Test: ")
                .append(test)
                .append(" ")
                .append(value)
                .append(" ")
                .append(valueDec).toString();
        String stringFormattedMethod = "Test: %s %d %s".formatted(test, value, valueDec);
        String messageFormatFormatMethod = MessageFormat.format("Test: {0} {1} {2}", test, value, valueDec);

        System.out.println("StringConcatenation value: " + stringConcatenation);
        System.out.println("StringBuilderAppending value: " + stringBuilderAppending);
        System.out.println("StringFormattedMethod value: " + stringFormattedMethod);
        System.out.println("MessageFormatFormatMethod value: " + messageFormatFormatMethod);*/
        /*Assertions.assertThat(concat).contains("field");
        Assertions.assertThat(concat).contains("static");
        Assertions.assertThat(concat).contains("BeforeClass");
        Assertions.assertThat(concat).contains("BeforeTest");
        Assertions.assertThat(concat).contains("Test2");*/
    }
}
