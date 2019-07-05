package au.id.lagod.dm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class XmlToMapConverter {
	private List<String> htmlElementWhiteList;
	

	public XmlToMapConverter(List<String> htmlElementWhiteList) {
		this.htmlElementWhiteList = htmlElementWhiteList;
	}
	
	public XmlToMapConverter() {
		this(new ArrayList<String>());
	}

	public XmlMap convertNodesFromXmlString(String xml)  {
	    InputStream is = new ByteArrayInputStream(xml.getBytes());  // implies UTF-8
	    return convertNodesFromXmlStream(is);
	}

	public  XmlMap convertNodesFromXmlStream(InputStream is) {
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader xmlReader = inputFactory.createXMLEventReader(is);
			
			ConverterState state = new ConverterState();
			
			while (xmlReader.hasNext()) {
				XMLEvent xmlEvent = xmlReader.nextEvent();
				
				if (xmlEvent.isStartElement()) {
					state.nestLevel++;
					
					if (state.nestLevel <= ConverterState.maxLevel) {
						StartElement elem = xmlEvent.asStartElement();
						state.startElement(elem);
						state.checkStartHtml(elem);
						
						if (state.inHtml()) {
							state.writeHtml(xmlEvent);
						}
					}
					
				}
				
				else if (xmlEvent.isEndElement()) {
					
					if (state.nestLevel <= ConverterState.maxLevel) {
						if (state.inHtml()) {
							state.writeHtml(xmlEvent);
							state.checkCloseHtml(xmlEvent.asEndElement());
						}
						else {
							state.closeStructLevel(xmlEvent.asEndElement());
						}
					}
					
					state.nestLevel--;
				}
				
				else if (xmlEvent.isCharacters()) {
					if (state.nestLevel <= ConverterState.maxLevel) {
						if (state.inHtml()) {
							state.writeHtml(xmlEvent);
						}
						else {
							state.addCharacters(xmlEvent);
						}
					}
				}
				
			}
			
			return state.getResult();
		}
		catch (XMLStreamException e) {
			throw new Error(e);
		}
	}
	
	private class ConverterState {
		private static final int maxLevel = 30;
		
		XmlMap result = new XmlMap();
		int nestLevel = 0;

		List<XmlMap> stack = new ArrayList<XmlMap>(maxLevel);
		List<String> nameStack = new ArrayList<String>(maxLevel);

		Integer htmlNestLevel = Integer.MAX_VALUE;
		StringWriter writer = new StringWriter();
		Object value;

		public ConverterState() {
			stack.add(result);
			nameStack.add("root");
			for (int i = 0; i < maxLevel; i++) {
				stack.add(null);
				nameStack.add(null);
			}
		}
		
		public XmlMap getResult() {
			return stack.get(0);
		}

		public void checkStartHtml(StartElement elem) {
			if (!inHtml() && htmlElementWhiteList.contains(elem.getName().getLocalPart())) {
				htmlNestLevel = nestLevel;
				writer = new StringWriter();
			}
		}

		public void checkCloseHtml(EndElement elem) {
			if (nestLevel <= htmlNestLevel) {
				value =  writer.toString();
				htmlNestLevel = Integer.MAX_VALUE;
			}
		}

		public boolean inHtml() {
			return nestLevel >= htmlNestLevel;
		}
		
		public void writeHtml(XMLEvent xmlEvent) throws XMLStreamException {
			xmlEvent.writeAsEncodedUnicode(writer);
		}

		public void startElement(StartElement elem) {
			String name = elem.getName().getLocalPart();
			XmlMap value = getMapFromElement(elem);  // start with a map so we can just keep adding children
			
			stack.set(nestLevel, value);
			nameStack.set(nestLevel, name);
			
			this.value = value;
		}

		private XmlMap getMapFromElement(StartElement elem) {
			XmlMap map = new XmlMap();
			
			Iterator<Attribute> iter = elem.getAttributes();
			while(iter.hasNext()) {
				Attribute attr = iter.next();
				map.put(attr.getName().getLocalPart(), attr.getValue());
			}
			
			return map;
		}

		public void closeStructLevel(EndElement elem) {
			Map<String, Object> parent = stack.get(nestLevel -1);
			String elementName = nameStack.get(nestLevel);
			
			// If we found no data for this element, we don't know whether 
			// it's an empty leaf node, empty list or empty struct, so set it to null
			if (Map.class.isAssignableFrom(value.getClass()) && ((Map) value).isEmpty()) {
				value = null;
			}
			
			// More than one element with this name, so we need to use a List instead of a Map
			if (parent.containsKey(elementName)) {
				Object existingValue = parent.get(elementName);
				
				if (existingValue != null && List.class.isAssignableFrom(existingValue.getClass())) {
					// Already a list, add the new value
					((List) existingValue).add(value);
				}
				else {
					// Convert the existing map to a list of one map, then add the new value
					List<Object> l = new ArrayList<Object>();
					l.add(existingValue);
					l.add(value);
					parent.put(elementName, l);
				}
				
			}
			// Only one element, stash it in the parent map
			else {
				parent.put(elementName, value);
			}
			
			// This amounts to popping the stack
			value = parent;
		}
		
		public void addCharacters(XMLEvent xmlEvent) {
			String chars = xmlEvent.asCharacters().getData();
			if (chars.matches("^\\s*$")) // ignore whitespace between tags
				return;
			
			// This will be the case where the parent tag has attributes
			if (value instanceof Map && ((Map) value).size() != 0) {
				((Map) value).put("value", chars);
			}
			// otherwise convert to a simple string
			else {
				value = xmlEvent.asCharacters().getData();
			}
		}

	}
	
	// Utility method to coerce a map value to a list
	// The parser only makes something into a list when it sees the second element 
	// of the same name.  That means a list of one object remains as a map or string.
	// This method turns it into a list and returns the result
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List listNode(Map<String, Object> map, String key) {
		Object value = map.get(key);
		
		if (value == null) 
			return new ArrayList();
		
		if (List.class.isAssignableFrom(value.getClass())) {
			return (List) value;
		}

		if (Map.class.isAssignableFrom(value.getClass()) || 
				String.class.isAssignableFrom(value.getClass())) {
			List list = new ArrayList();
			list.add(value);
			map.put(key,  list);
			return list;
		}

		throw new Error("listNode called on non-list");
	}
	
	public static String getString(Map<String, Object> map, String key) {
		if (map == null)
			return null;
		Object value = map.get(key);
		if (value == null)
			return null;
		if (value instanceof List)
			return ((List<String>) value).get(0);
		return value.toString();
	}
	

}
