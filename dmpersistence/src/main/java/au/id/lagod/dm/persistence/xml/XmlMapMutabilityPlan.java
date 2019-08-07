package au.id.lagod.dm.persistence.xml;

import org.hibernate.type.descriptor.java.MutableMutabilityPlan;

import au.id.lagod.dm.xml.XmlMap;
import au.id.lagod.dm.xml.XmlMapConverter;

public class XmlMapMutabilityPlan<T extends XmlMap> extends MutableMutabilityPlan<T> {
	private static final long serialVersionUID = 1L;
	private XmlMapConverter<T> converter;

	public XmlMapMutabilityPlan(XmlMapConverter<T> xmlMapConverter) {
		this.converter = xmlMapConverter;
	}

	@Override
	public T deepCopyNotNull(T value) {
    	String valueString = converter.toString(value);
    	T copy = converter.fromString(valueString);
        return copy;
	}

}
