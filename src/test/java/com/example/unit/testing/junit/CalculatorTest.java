package com.example.unit.testing.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculatorTest {

    Calculator calculator;
    @BeforeEach
    public void setUp(){
        calculator=new Calculator();
    }

    @Test
    public void testMultiply(){
        assertEquals(20,calculator.multiply(4,5));
        assertEquals(25,calculator.multiply(5,5));
    }

}
