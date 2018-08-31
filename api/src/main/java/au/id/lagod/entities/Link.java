package au.id.lagod.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Link {
	
	public String href;
	public String title;
	
	public Link() {};
	
	public Link(String href) {
		this.href = href;
	}

}
