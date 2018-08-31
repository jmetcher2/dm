package au.id.lagod.entities;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;;

/*
 * This class pretty much does nothing at the moment.  We'll remove it if no reason to keep it emerges.
 * 
 * The XmlTransient notation tells JAXB that we want this class to be invisible to the marshaller.
 * Otherwise the superclass/subclass relationship would appear in the emitted representation.
 * Note, however, that XmlTransient is NOT transitive - that is, members of this class can still be marshalled 
 * even if the class definition itself is not.
 * 
 */
@XmlTransient
@JsonInclude(Include.NON_NULL)
public class BaseModel {
	
	/*
	 * The default marshalling would be yyyy-mm-dd.  The annotation tells JAXB to 
	 * add the timestamp.
	 */
	
	@XmlSchemaType(name = "dateTime")
	protected Date timestamp = new Date();

	// No-arg constructor for unmarshalling
	public BaseModel() {}
	
	public BaseModel(UriInfo uriInfo) {
		this._links = new Links(uriInfo);
	}

	protected Links _links;
	
	@XmlElement(name="_links")
	public Map<String, Link> getLinks() {
		return _links.links;
	}

	protected LinkParameter param(String key, Object value) {
		return new LinkParameter(key, value);
	}
	
}
