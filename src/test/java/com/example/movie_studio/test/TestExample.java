package com.example.movie_studio.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestExample {
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all method");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each method");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after All method");
    }

    @Test
    void testMethod() {
        System.out.println("test method");
    }
}
