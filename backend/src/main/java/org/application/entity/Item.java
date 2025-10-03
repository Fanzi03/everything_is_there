package org.application.entity;

import java.util.Set;
import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

import org.application.enums.ItemTag;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "items")
@Accessors(chain = true) 
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	UUID id;

	@Column(name = "name", nullable = false)
	String name;

	@Column(name = "description")
	String description;

	@Column(name = "primary_tag")
	@Enumerated(EnumType.STRING)
	ItemTag primaryTag;

	@ElementCollection(targetClass = ItemTag.class)
	@CollectionTable(
		name = "item_tags",
		joinColumns = @JoinColumn(name = "item_id")
	)
	@Column(name = "tag")
	@Enumerated(EnumType.STRING)
	@Builder.Default
	Set<ItemTag> tags = EnumSet.noneOf(ItemTag.class);

	public void setPrimaryTag(ItemTag primaryTag){
		this.primaryTag = primaryTag;

		if(primaryTag != null){
			this.tags.add(primaryTag);
		}
	}

	public void removeTag(ItemTag tag){
		this.tags.remove(tag);

		if(Objects.equals(primaryTag, tag)){
			primaryTag = null;
		}
	}

	public static class ItemBuilder{
		public ItemBuilder primaryTag(ItemTag primaryTag){
			this.primaryTag = primaryTag;	
			
			if(primaryTag != null){
				if(!this.tags$set){
					this.tags$value = EnumSet.noneOf(ItemTag.class);
					this.tags$set = true;
				}
				this.tags$value.add(primaryTag);
			}

			return this;
		}

		public ItemBuilder tags(Set<ItemTag> tags){
			if(tags == null){
				this.tags$value = EnumSet.noneOf(ItemTag.class);
			}
			else {
				this.tags$value = EnumSet.copyOf(tags);
			}

			if(this.primaryTag != null){
				this.tags$value.add(primaryTag);
			}

			this.tags$set = true;

			return this;
		}

		
	}

}
