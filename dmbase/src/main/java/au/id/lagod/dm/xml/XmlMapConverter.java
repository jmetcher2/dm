package au.id.lagod.dm.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

public class XmlMapConverter<T extends XmlMap> {
	private String rootElementName;
	private String namespace;
	private List<String> whitelistAllowedHtmlElementsList;
	private Class<T> clazz;

	public XmlMapConverter(Class<T> clazz, List<String> whitelistAllowedHtmlElementsList2, String rootElementName2,
			String namespace2) {
		this.whitelistAllowedHtmlElementsList = whitelistAllowedHtmlElementsList2;
		this.rootElementName = rootElementName2;
		this.namespace = namespace2;
		this.clazz = clazz;
	}

    @SuppressWarnings("unchecked")
	public String toString(T value) {
    	try {
    		if (value == null) 
    			return null;
			return new MapToXmlConverter().convert(rootElementName, namespace, (Map<String, Object>) value.get(rootElementName));
		} catch (XMLStreamException e) {
			throw new Error(e);
		}
    }

    public T fromString(String string) {
		XmlMap xm =  new XmlToMapConverter(whitelistAllowedHtmlElementsList).convertNodesFromXmlString(string);
		try {
			return clazz.getConstructor(XmlMap.class).newInstance(xm);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new Error(e);
		}
    }

}
