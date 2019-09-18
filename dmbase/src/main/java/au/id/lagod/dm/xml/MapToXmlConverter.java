package au.id.lagod.dm.xml;

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class MapToXmlConverter {
	
	private Map<Class, XmlObjectConverter> converters = new HashMap<Class, XmlObjectConverter>();

	public MapToXmlConverter(Map<Class, XmlObjectConverter> converters) {
		this.converters = converters;
	}

	public MapToXmlConverter() {
	}

	public String convert( String name, String namespace, Map<String, Object> map) throws XMLStreamException {
		StringWriter writer = new StringWriter();
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlWriter = outputFactory.createXMLStreamWriter(writer);
		
		convertMap(name, namespace, map, xmlWriter);
		return writer.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void convertObject(String name, Object o, XMLStreamWriter xmlWriter) throws XMLStreamException {
			if (o != null && converters.containsKey(o.getClass())) {
				converters.get(o.getClass()).convert(name, o, xmlWriter);
			}
			else if (o instanceof Map) {
				convertMap(name, "", (Map<String, Object>) o, xmlWriter);
			}
			else if (o instanceof Collection) {
				convertCollection(name, (Collection) o, xmlWriter);
			}
			else {
				convertScalar(name, o, xmlWriter);
			}
	}

	private void convertMap(String name, String namespace, Map<String, Object> map, XMLStreamWriter xmlWriter) throws XMLStreamException {
		if (namespace != "") {
			xmlWriter.writeStartElement("", name, namespace);
			xmlWriter.writeDefaultNamespace(namespace);
		}
		else {
			xmlWriter.writeStartElement(name);
		}
		if (map != null) {
			if (map.containsKey("id")) {
				xmlWriter.writeAttribute("id", map.get("id").toString());
			}
			for (Map.Entry<String, Object> e2: map.entrySet()) {
				if (!e2.getKey().equals("id")) {
					convertObject(e2.getKey(), e2.getValue(), xmlWriter);
				}
			}
		}
		xmlWriter.writeEndElement();
	}
	
	private void convertScalar(String name, Object o, XMLStreamWriter xmlWriter) throws XMLStreamException {
		xmlWriter.writeStartElement(name);
		xmlWriter.writeCharacters(o == null ? "null" : o.toString());
		xmlWriter.writeEndElement();
	}

	private void convertCollection(String name, Collection<Object> c, XMLStreamWriter xmlWriter) throws XMLStreamException {
		for (Object item: c) {
			convertObject(name, item, xmlWriter);
		}
	}
	
	

}
