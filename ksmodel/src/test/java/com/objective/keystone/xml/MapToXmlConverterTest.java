package com.objective.keystone.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import au.id.lagod.dm.MapToXmlConverter;
import au.id.lagod.dm.XmlToMapConverter;

public class MapToXmlConverterTest {
	
	@Test
	public void test() throws XMLStreamException {
		XmlToMapConverter xmlConverter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<metadata xmlns=\"http://www.limehousesoftware.co.uk\"><description>test description</description><mailset>inherited</mailset><mailsetName>Browse for Mail Set</mailsetName><privateToPublic>false</privateToPublic><formalSubmission>true</formalSubmission><responseContent><p class='xyz'>blah <strong>x</strong> blah</p></responseContent><privacyStatement><p>This event is not private</p></privacyStatement><singleResponse>false</singleResponse><metadata id=\"11\"><address/><name>Consultation Home</name><organisation/><replyTo/><telephone>sdfsdfsdf</telephone></metadata><counts cachedOn=\"2018-10-17 00:09:29.285\"><comments><pending><totals all=\"11\" month=\"1\" week=\"1\" day=\"1\"/></pending><not_submitted><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></not_submitted><duly_made><totals all=\"5\" month=\"5\" week=\"0\" day=\"0\"/></duly_made><rejected><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></rejected></comments><consultees><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></consultees></counts><metadata id=\"209233\"><question-1>test</question-1></metadata><metadata id=\"14516\"><question-1><answer>Cherwell</answer><answer>Adderbury</answer></question-1><question-2><answer>Businesses</answer><answer>Central Government</answer><answer>Community &amp; Groups</answer></question-2><question-3/></metadata></metadata>";
		Map<String, Object> map = xmlConverter.convertNodesFromXmlString(xml);

		MapToXmlConverter converter = new MapToXmlConverter();
		
		String result = converter.convert("metadata", "", (Map<String, Object>) map.get("metadata"));
		System.out.println(map.toString());
		System.out.println(result);
	}

	@Test
	public void testNamespace() throws XMLStreamException {
		XmlToMapConverter xmlConverter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<metadata xmlns=\"http://www.limehousesoftware.co.uk\"><description>test description</description><mailset>inherited</mailset><mailsetName>Browse for Mail Set</mailsetName><privateToPublic>false</privateToPublic><formalSubmission>true</formalSubmission><responseContent><p class='xyz'>blah <strong>x</strong> blah</p></responseContent><privacyStatement><p>This event is not private</p></privacyStatement><singleResponse>false</singleResponse><metadata id=\"11\"><address/><name>Consultation Home</name><organisation/><replyTo/><telephone>sdfsdfsdf</telephone></metadata><counts cachedOn=\"2018-10-17 00:09:29.285\"><comments><pending><totals all=\"11\" month=\"1\" week=\"1\" day=\"1\"/></pending><not_submitted><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></not_submitted><duly_made><totals all=\"5\" month=\"5\" week=\"0\" day=\"0\"/></duly_made><rejected><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></rejected></comments><consultees><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></consultees></counts><metadata id=\"209233\"><question-1>test</question-1></metadata><metadata id=\"14516\"><question-1><answer>Cherwell</answer><answer>Adderbury</answer></question-1><question-2><answer>Businesses</answer><answer>Central Government</answer><answer>Community &amp; Groups</answer></question-2><question-3/></metadata></metadata>";
		Map<String, Object> map = xmlConverter.convertNodesFromXmlString(xml);

		MapToXmlConverter converter = new MapToXmlConverter();
		
		String result = converter.convert("metadata", "http://www.limehousesoftware.co.uk", (Map<String, Object>) map.get("metadata"));
		System.out.println(map.toString());
		System.out.println(result);
	}

	@Test
	public void testEmpty() throws XMLStreamException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metadata", new HashMap<String, Object>());

		MapToXmlConverter converter = new MapToXmlConverter();
		
		String result = converter.convert("metadata", "", (Map<String, Object>) map.get("metadata"));
		System.out.println(map.toString());
		System.out.println(result);
	}

}
