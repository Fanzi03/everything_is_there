package org.application.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class ItemDataTransferObject {
	
	UUID id;

	@NotBlank(message = "name is required")
	@Size(min = 2, max = 55, message = "size must be normal (2-55)")
	String name;

	@Size(min = 3, max = 55, message = "tag must be normal (3-55)")
	String primaryTag;

	@Size(max = 150, message = "description must be less 150")
	String description;

	@Builder.Default
	List<String> tags = new ArrayList<>();

	public void setPrimaryTag(String primaryTag){
		this.primaryTag = primaryTag;

		if(primaryTag != null){
			this.tags.add(primaryTag);
		}
	}	

	public void removeTag(String tag){
		this.tags.remove(tag);

		if(Objects.equals(tag, primaryTag)){
			primaryTag = null;
		}
	}

	public static class ItemDataTransferObjectBuilder{
		public ItemDataTransferObjectBuilder primaryTag(String primaryTag){
			this.primaryTag = primaryTag;

			if(primaryTag != null){
				if(!this.tags$set){
					this.tags$value = new ArrayList<>();
					this.tags$set = true;
				}
				this.tags$value.add(primaryTag);
			}

			return this;
		}

		public ItemDataTransferObjectBuilder tags(List<String> tags){
			if(tags == null){
				this.tags$value = new ArrayList<>();
			}else{
				this.tags$value = new ArrayList<>(tags);
			}

			if(this.primaryTag != null && !this.tags$value.contains(this.primaryTag)){
				this.tags$value.add(this.primaryTag);
			}

			this.tags$set = true;

			return this;
		}
	}

}
