package org.application.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemTag {

	NEW, POPULAR, SALE;

	@JsonCreator
	public static ItemTag fromString(String tag){
		if(tag == null || tag.isEmpty()){
			throw new IllegalArgumentException("Primary tag cannot be null or empty") {};
		}	

		try{
			return ItemTag.valueOf(tag.toUpperCase());
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException("Primary tag is not correct " + tag);
		}
	}

	@JsonValue
	public String toValue(){
		return this.name().toLowerCase();
	}
}
