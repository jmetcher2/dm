package com.objective.keystone.persistence;

import com.objective.keystone.xml.XmlConstants;

import au.id.lagod.dm.persistence.xml.XmlMapBasicType;
import au.id.lagod.dm.persistence.xml.XmlMapBasicTypeDescriptor;

public class MetadataMapBasicType extends XmlMapBasicType {
	
	private static final long serialVersionUID = 1L;
	private static final XmlMapBasicTypeDescriptor DESCRIPTOR = new XmlMapBasicTypeDescriptor(XmlConstants.WHITELIST_ALLOWED_HTML_ELEMENTS_LIST, "metadata", "http://www.limehousesoftware.co.uk"); 
	
	public MetadataMapBasicType() {
		super(DESCRIPTOR);
	}

}
