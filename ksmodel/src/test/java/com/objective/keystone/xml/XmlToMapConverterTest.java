package com.objective.keystone.xml;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import au.id.lagod.dm.XmlToMapConverter;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class XmlToMapConverterTest {

	@Test
	public void test() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<metadata xmlns=\"http://www.limehousesoftware.co.uk\"><description>test description</description><mailset>inherited</mailset><mailsetName>Browse for Mail Set</mailsetName><privateToPublic>false</privateToPublic><formalSubmission>true</formalSubmission><responseContent><p class='xyz'>blah <strong>x</strong> blah</p></responseContent><privacyStatement><p>This event is not private</p></privacyStatement><singleResponse>false</singleResponse><metadata id=\"11\"><address/><name>Consultation Home</name><organisation/><replyTo/><telephone>sdfsdfsdf</telephone></metadata><counts cachedOn=\"2018-10-17 00:09:29.285\"><comments><pending><totals all=\"11\" month=\"1\" week=\"1\" day=\"1\"/></pending><not_submitted><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></not_submitted><duly_made><totals all=\"5\" month=\"5\" week=\"0\" day=\"0\"/></duly_made><rejected><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></rejected></comments><consultees><totals all=\"0\" month=\"0\" week=\"0\" day=\"0\"/></consultees></counts><metadata id=\"209233\"><question-1>test</question-1></metadata><metadata id=\"14516\"><question-1><answer>Cherwell</answer><answer>Adderbury</answer></question-1><question-2><answer>Businesses</answer><answer>Central Government</answer><answer>Community &amp; Groups</answer></question-2><question-3/></metadata></metadata>";
		
		converter.convertNodesFromXmlString(xml);
	}

	@Test
	public void testHtml() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x><aa><a>b</a></aa></x>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		result = (Map)result.get("x");
		
		assertEquals("<a>b</a>", result.get("aa"));
	}

	@Test
	public void testHtml2() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x><aa><a>b</a></aa><bb><a>c</a></bb></x>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		result = (Map)result.get("x");
		
		assertEquals("<a>b</a>", result.get("aa"));
		assertEquals("<a>c</a>", result.get("bb"));
	}
	
	@Test
	public void testHtml3() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x><p><a>b</a><a>c</a></p></x>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		System.out.println(result);
		
		assertEquals("<p><a>b</a><a>c</a></p>", result.get("x"));
	}
	
	@Test
	public void testEmptyElement() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x><aa></aa><bb><a>c</a></bb></x>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		result = (Map)result.get("x");
		
		assertEquals(null, result.get("aa"));
		assertEquals("<a>c</a>", result.get("bb"));
	}
	
	@Test
	public void testEmptyElement2() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x><aa/><bb><a>c</a></bb></x>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		result = (Map)result.get("x");
		
		assertEquals(null, result.get("aa"));
		assertEquals("<a>c</a>", result.get("bb"));
	}
	
	@Test
	public void testEmptyElement3() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x/>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		result = (Map)result.get("x");
		
		assertEquals(null, result);
	}
	
	@Test
	public void testList() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x><aa><a>b</a></aa><aa><a>c</a></aa></x>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		result = (Map)result.get("x");
		
		List aa = (List)result.get("aa");
		assertEquals("<a>b</a>", aa.get(0));
		assertEquals("<a>c</a>", aa.get(1));
	}
	
	@Test
	public void testEmptListItem() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x><aa/><aa><a>b</a></aa><aa/></x>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		result = (Map)result.get("x");
		
		List aa = (List)result.get("aa");
		assertEquals(null, aa.get(0));
		assertEquals("<a>b</a>", aa.get(1));
	}
	
	@Test
	public void testListOfLists() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<x><bb><aa><a>b</a></aa><aa><a>c</a></aa></bb><bb><aa><a>c</a></aa><aa><a>d</a></aa></bb></x>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		result = (Map)result.get("x");
		
		List bb = (List)result.get("bb");
		Map bb0 = (Map) bb.get(0);
		List aa = (List) bb0.get("aa");
		assertEquals("<a>b</a>", aa.get(0));
		assertEquals("<a>c</a>", aa.get(1));
		
	}
	
	@Test 
	public void testRep() throws Exception {
		XmlToMapConverter converter = new XmlToMapConverter(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST);
		String xml = "<representationXml xmlns=\"http://www.limehousesoftware.co.uk\">\n" + 
				"  <question-9>5</question-9>\n" + 
				"  <question-7 name=\"temp.html\" size=\"1024\" type=\"text/html\">1249075</question-7>\n" + 
				"  <question-2>yes</question-2>\n" + 
				"</representationXml>";
		
		Map<String, Object> result = converter.convertNodesFromXmlString(xml);
		System.out.println(result);
		
	}
	
}
