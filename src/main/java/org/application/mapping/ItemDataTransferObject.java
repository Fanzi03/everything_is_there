package org.application.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class ItemDataTransferObject {
	
	UUID id;

	@NotBlank(message = "name is required")
	@Size(min = 2, max = 55, message = "size must be normal (2-55)")
	String name;

	@Size(min = 3, max = 55, message = "tag must be normal (3-55)")
	String primaryTag;

	@Builder.Default
	List<String> tags = new ArrayList<>();

}
