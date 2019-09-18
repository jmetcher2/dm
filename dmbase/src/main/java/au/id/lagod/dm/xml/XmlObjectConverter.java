package au.id.lagod.dm.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface XmlObjectConverter<T> {
	
	public void convert(String name, T input, XMLStreamWriter writer) throws XMLStreamException;

}
