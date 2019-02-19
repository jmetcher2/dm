package au.id.lagod.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import au.id.lagod.jersey_poc.links.Links;
import au.id.lagod.jersey_poc.services.BaseService;

/*
 * The XmlTransient notation tells JAXB that we want this class to be invisible to the marshaller.
 * Otherwise the superclass/subclass relationship would appear in the emitted representation.
 * Note, however, that XmlTransient is NOT transitive - that is, members of this class can still be marshalled 
 * even if the class definition itself is not.
 * 
 */
@XmlTransient
@JsonInclude(Include.NON_NULL)
public class BaseDTO {
	
	/*
	 * The default marshalling would be yyyy-mm-dd.  The annotation tells JAXB to 
	 * add the timestamp.
	 */
	
	@XmlSchemaType(name = "dateTime")
	protected Date timestamp;
	
	private BaseService service;
	
	public BaseDTO() {}

	public BaseDTO(boolean embedded, BaseService service) {
		this.timestamp = embedded ? null : new Date();
		this.service = service;
	}
	
	public String selfLink; // link to the endpoint that created this DTO
	public Links _links = new Links();
	
}
