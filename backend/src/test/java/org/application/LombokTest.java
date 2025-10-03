package org.application;

import org.junit.jupiter.api.Test;

import lombok.Getter;

@Getter
public class LombokTest {

	String work = "hello";

	@Test
	void isWorkTest(){
		new LombokTest().getWork();
	}
		
}
