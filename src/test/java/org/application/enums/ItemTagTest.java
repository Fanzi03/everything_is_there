package org.application.enums;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@Timeout(value = 4, unit = TimeUnit.SECONDS)
public class ItemTagTest {

	@ParameterizedTest 
	@EnumSource(ItemTag.class)
	@DisplayName("2025-09-07")
	void shouldHaveActualTag(ItemTag tag){
		assertThat(tag).isNotNull();
		assertThat(tag).isIn(ItemTag.POPULAR, ItemTag.NEW, ItemTag.SALE);
	}

	
	@ParameterizedTest
	@EnumSource(ItemTag.class)
	@DisplayName("check fromString")
	void shouldCheckFromString(ItemTag tag){
		String rightLowerTag = tag.toString().toLowerCase();
		String wrongLowerTag = "unknown tag";
		String blankTag = " ";
		String nullTag = null;

		ItemTag tagCheck = ItemTag.fromString(rightLowerTag);
		assertThat(tagCheck.toString().toLowerCase()).isEqualTo(rightLowerTag);

		assertThrows(IllegalArgumentException.class, () -> ItemTag.fromString(wrongLowerTag));
		assertThrows(IllegalArgumentException.class, () -> ItemTag.fromString(blankTag));
		assertThrows(IllegalArgumentException.class, () -> ItemTag.fromString(nullTag));
	}

	@ParameterizedTest
	@EnumSource(ItemTag.class)
	@DisplayName("check toValue")
	void shouldCheckToValue(ItemTag tag){
		assertThat(tag.toValue().toString()).isEqualTo(tag.toString().toLowerCase());
	}



}
