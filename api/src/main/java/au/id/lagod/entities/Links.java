package au.id.lagod.entities;

import java.util.HashMap;

import java.util.Map;

import au.id.lagod.jersey_poc.links.LinkSpec;

public class Links {
	
	public Map<String, Link> links = new HashMap<String, Link>();

	public Map<String, LinkSpec> linkSpecs = new HashMap<String, LinkSpec>();
	
	public void addLink(String rel, LinkSpec link) {
		linkSpecs.put(rel,  link);
	}
	
}
