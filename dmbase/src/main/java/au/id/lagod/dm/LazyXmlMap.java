package au.id.lagod.dm;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

/**
 * Map backed by an xml string, where we only convert between the two when 
 * we need to read a particular format
 *
 */
public class LazyXmlMap {
	String xml;
	Map<String, Object> map = new HashMap<String, Object>();
	
	boolean xmlStale = true;
	boolean mapStale = true;
	private String rootElementName;
	private String namespace;
	
	public LazyXmlMap() {}
	
	public LazyXmlMap(String rootElementName, String namespace, String xml) {
		this.rootElementName = rootElementName;
		this.namespace = namespace;
		this.xml = xml;
		mapStale = true;
		xmlStale = false;
	}
	
	public LazyXmlMap(String rootElementName, String namespace, Map<String, Object> map) {
		this.rootElementName = rootElementName;
		this.namespace = namespace;
		this.map = map;
		mapStale = false;
		xmlStale = true;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap() {
		if (mapStale) {
			map = new XmlToMapConverter().convertNodesFromXmlString(xml);
			mapStale = false;
		}
		return new HashMap<String, Object>((Map<String, Object>) map.get(rootElementName));
	}
	
	public String getXml() throws XMLStreamException {
		if (xmlStale) {
			xml = new MapToXmlConverter().convert(rootElementName, namespace, map);
			xmlStale = false;
		}
		return xml;
	}
	
	public void setMap(Map<String, Object> newMap) {
		this.map = newMap;
		xmlStale = true;
		mapStale = false;
	}
	
	public void setXml(String xml) {
		this.xml = xml;
		xmlStale = false;
		mapStale = true;
	}
	
	public boolean isInitialized() {
		return !xmlStale || !mapStale;
	}
}

