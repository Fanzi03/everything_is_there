package org.application.mapping;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(value = 2, unit = TimeUnit.SECONDS)
public class ItemDataTransferObjectTest {

	ItemDataTransferObject itemDto;	

	@BeforeEach
	void setUp(){
		itemDto = new ItemDataTransferObject();
	}

	@Test
	void testField(){
		itemDto = ItemDataTransferObject.builder().name("test").id(UUID.randomUUID()).build();

		assertThat("test").isEqualTo(itemDto.getName());
		assertThat(itemDto.getId()).isNotNull();		
	}
}
