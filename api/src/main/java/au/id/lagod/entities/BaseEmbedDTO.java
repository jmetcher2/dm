package au.id.lagod.entities;

import java.util.Date;

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
public class BaseEmbedDTO {
	
	private BaseService service;
	
	public BaseEmbedDTO() {}

	public BaseEmbedDTO(BaseService service) {
		this.service = service;
	}
	
	@JsonInclude(Include.NON_EMPTY)
	public Links _links = new Links();

}
