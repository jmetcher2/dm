package com.objective.keystone.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.objective.keystone.model.folder.FolderType;

@Converter
public class FolderTypeConverter implements AttributeConverter<FolderType, String> {

	@Override
	public String convertToDatabaseColumn(FolderType attribute) {
		return attribute.toString();
	}

	@Override
	public FolderType convertToEntityAttribute(String dbData) {
		return FolderType.fromString(dbData);
	}

}
