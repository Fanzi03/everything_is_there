package org.application.entity;

import java.util.UUID;

import org.application.enums.ItemTag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	UUID id;

	@Column(name = "name", nullable = false)
	String name;

	@Column(name = "primary_tag")
	@Enumerated(EnumType.STRING)
	ItemTag primaryTag;
	
}
