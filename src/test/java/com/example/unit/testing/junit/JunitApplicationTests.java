package com.example.unit.testing.junit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JunitApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
