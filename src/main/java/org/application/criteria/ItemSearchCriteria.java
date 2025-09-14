package org.application.criteria;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Accessors(chain = true)
public class ItemSearchCriteria {

	String description;
	String primaryTag;

	@Builder.Default
	int page = 0;

	@Builder.Default
	int size = 10;

	public boolean hasDescription(){
		return description != null && !description.trim().isEmpty();
	}

	public boolean hasPrimaryTag(){
		return primaryTag != null && !primaryTag.trim().isEmpty();
	}
	
}
