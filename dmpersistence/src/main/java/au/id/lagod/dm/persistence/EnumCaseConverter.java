package au.id.lagod.dm.persistence;

import javax.persistence.AttributeConverter;

public abstract class EnumCaseConverter<T extends Enum<T>> implements AttributeConverter<T, String>{
	
	private Class<T> clazz;

	public EnumCaseConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String convertToDatabaseColumn(T attribute) {
		if (attribute == null) return null;
		return attribute.toString().toLowerCase();
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		if (dbData == null) return null;
		return Enum.valueOf(clazz, dbData.toUpperCase());
	}

}
