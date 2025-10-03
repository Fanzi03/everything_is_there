package org.application.criteria;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Size;
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

	@Size(max = 150, message = "description must be < 150.length")
	String description;

	@Size(max = 55, message = "primaryTag must be < 55.length")
	String primaryTag;

	@Builder.Default
	List<String> tags = new ArrayList<>();

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

	public boolean hasTags(){
		return tags != null && !tags.isEmpty();
	}
	
}
