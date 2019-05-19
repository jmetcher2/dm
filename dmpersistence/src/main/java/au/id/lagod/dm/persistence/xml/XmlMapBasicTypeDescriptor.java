package au.id.lagod.dm.persistence.xml;

import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;

import au.id.lagod.dm.MapToXmlConverter;
import au.id.lagod.dm.XmlMap;
import au.id.lagod.dm.XmlToMapConverter;

public class XmlMapBasicTypeDescriptor extends AbstractTypeDescriptor<XmlMap> {
	private static final long serialVersionUID = 1L;
	private String rootElementName;
	private String namespace;
	private List<String> whitelistAllowedHtmlElementsList;
	
	public XmlMapBasicTypeDescriptor(List<String> whitelistAllowedHtmlElementsList, String rootElementName, String namespace) {
        super( XmlMap.class, new MutableMutabilityPlan<XmlMap>() {
            @Override
            protected XmlMap deepCopyNotNull(XmlMap value) {
            	XmlMap copy = new XmlMap();
            	copy.putAll((XmlMap) value);
                return copy;
            }
        });
        this.rootElementName = rootElementName;
        this.namespace = namespace;
        this.whitelistAllowedHtmlElementsList = whitelistAllowedHtmlElementsList;
    }

	@Override
	public boolean areEqual(XmlMap one, XmlMap another) {
		// To deep clone or deep compare two maps is going to take
		// just as long as writing it out each time, so we might as well
		// regard map fields as perpetually dirty
		return false;
	}



	@Override
    public String toString(XmlMap value) {
		return toString(value, rootElementName, namespace);
	}
	
    @SuppressWarnings("unchecked")
	public String toString(XmlMap value, String rootElementName, String namespace) {
    	try {
    		if (value == null) 
    			return null;
			return new MapToXmlConverter().convert(rootElementName, namespace, (Map<String, Object>) value.get(rootElementName));
		} catch (XMLStreamException e) {
			throw new Error(e);
		}
    }

    @Override
    public XmlMap fromString(String string) {
    	try {
			return new XmlToMapConverter(whitelistAllowedHtmlElementsList).convertNodesFromXmlString(string);
		} catch (XMLStreamException e) {
			throw new Error(e);
		}
    }

    
	@SuppressWarnings({"unchecked"})
    public <X> X unwrap(XmlMap value, Class<X> type, WrapperOptions options) {
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

    public <X> XmlMap wrap(X value, WrapperOptions options) {
        if ( value == null ) {
            return null;
        }
        if ( String.class.isInstance( value ) ) {
            return fromString( (String) value );
        }
        if ( XmlMap.class.isInstance( value ) ) {
            return (XmlMap) value;
        }
        throw unknownWrap( value.getClass() );
    }
}
