package au.id.lagod.dm.persistence.xml;

import java.util.List;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import au.id.lagod.dm.xml.XmlMap;
import au.id.lagod.dm.xml.XmlMapConverter;

public class XmlMapBasicTypeDescriptor<T extends XmlMap> extends AbstractTypeDescriptor<T> {
	private static final long serialVersionUID = 1L;
	private Class<T> clazz;
	private XmlMapConverter<T> converter;
	
	
	public XmlMapBasicTypeDescriptor(Class<T> clazz, List<String> whitelistAllowedHtmlElementsList, String rootElementName, String namespace) {
		super( clazz, new XmlMapMutabilityPlan<T>(new XmlMapConverter<T>(clazz, whitelistAllowedHtmlElementsList, rootElementName, namespace)));
		
		this.converter = new XmlMapConverter<T>(clazz, whitelistAllowedHtmlElementsList, rootElementName, namespace);

        this.clazz = clazz;
    }

	@Override
	public boolean areEqual(T one, T another) {
		if (one == null) 
			return false;
		return one.equals(another);
	}


	@Override
	public T fromString(String string) {
		return converter.fromString(string);
	}

	@Override
    public String toString(T value) {
		return converter.toString(value);
	}
	
	@SuppressWarnings({"unchecked"})
    public <X> X unwrap(T value, Class<X> type, WrapperOptions options) {
        if ( value == null ) {
            return null;
        }
        if ( XmlMap.class.isAssignableFrom( type ) ) {
            return (X) value;
        }
        if ( String.class.isAssignableFrom( type ) ) {
            return (X) toString( value);
        }
        throw unknownUnwrap( type );
    }

    public <X> T wrap(X value, WrapperOptions options) {
        if ( value == null ) {
            return null;
        }
        if ( String.class.isInstance( value ) ) {
            return fromString( (String) value );
        }
        if ( clazz.isInstance( value ) ) {
            return (T) value;
        }
        throw unknownWrap( value.getClass() );
    }

}
