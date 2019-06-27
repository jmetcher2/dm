package au.id.lagod.dm.persistence.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;

import au.id.lagod.dm.MapToXmlConverter;
import au.id.lagod.dm.XmlMap;
import au.id.lagod.dm.XmlToMapConverter;

public class XmlMapBasicTypeDescriptor<T extends XmlMap> extends AbstractTypeDescriptor<T> {
	private static final long serialVersionUID = 1L;
	private String rootElementName;
	private String namespace;
	private List<String> whitelistAllowedHtmlElementsList;
	private Class<T> clazz;
	
	public XmlMapBasicTypeDescriptor(Class<T> clazz, List<String> whitelistAllowedHtmlElementsList, String rootElementName, String namespace) {
        super( clazz, new MutableMutabilityPlan<T>() {
            @Override
            protected T deepCopyNotNull(T value) {
            	T copy;
				try {
					copy = clazz.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new Error(e);
				}
            	copy.putAll(value);
                return copy;
            }
        });
        this.rootElementName = rootElementName;
        this.namespace = namespace;
        this.whitelistAllowedHtmlElementsList = whitelistAllowedHtmlElementsList;
        this.clazz = clazz;
    }

	@Override
	public boolean areEqual(T one, T another) {
		// To deep clone or deep compare two maps is going to take
		// just as long as writing it out each time, so we might as well
		// regard map fields as perpetually dirty
		return false;
	}



	@Override
    public String toString(T value) {
		return toString(value, rootElementName, namespace);
	}
	
    @SuppressWarnings("unchecked")
	public String toString(T value, String rootElementName, String namespace) {
    	try {
    		if (value == null) 
    			return null;
			return new MapToXmlConverter().convert(rootElementName, namespace, (Map<String, Object>) value.get(rootElementName));
		} catch (XMLStreamException e) {
			throw new Error(e);
		}
    }

    @Override
    public T fromString(String string) {
		XmlMap xm =  new XmlToMapConverter(whitelistAllowedHtmlElementsList).convertNodesFromXmlString(string);
		try {
			return clazz.getConstructor(XmlMap.class).newInstance(xm);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new Error(e);
		}
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
