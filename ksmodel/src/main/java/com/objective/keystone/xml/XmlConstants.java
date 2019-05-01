package com.objective.keystone.xml;

import java.util.Arrays;
import java.util.List;

public final class XmlConstants {

	public static final String[] WHITELIST_ALLOWED_HTML_ELEMENTS = new String[] {"p","div","ul","ol","li","b","i","strong",
		"em","blockquote","strike","h1","h2","h3","h4","h5","h6","hr","small","span","sub","sup","pre",
		"td","table","tbody","tfoot","th","thead","tr","u","br","a"};
	
	public static final List<String> WHITELIST_ALLOWED_HTML_ELEMENTS_LIST = Arrays.asList(WHITELIST_ALLOWED_HTML_ELEMENTS);

	private XmlConstants() {
		// restrict instantiation
	}

}
