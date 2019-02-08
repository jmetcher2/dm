package au.id.lagod.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.objective.dm.base.BaseDomainObject;

import au.id.lagod.jersey_poc.services.BaseService;

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
	
	public Map<String, Object> _links = new HashMap<String, Object>();
	
	protected String link(Class<?> clazz) {
		UriBuilder uriPath = service.getUriInfo().getBaseUriBuilder().path(clazz);
		return uriPath.build().toString();
	}

	protected <T extends BaseDomainObject> String link(Class<? extends BaseService> clazz, String methodName,  T... params) {
		UriBuilder uriPath = service.getUriInfo().getBaseUriBuilder().path(clazz).path(clazz, methodName);
		for(int  i=0; i< params.length;i++){
			uriPath = uriPath.resolveTemplate(service.paramName(params[i]), params[i].getTextKey());
		}
		return uriPath.build().toString();
	}

	protected <T extends BaseDomainObject> String link(String methodName,  T... params) {
		return link(service.getClass(), methodName, params);
	}

	protected String link() {
		return link(service.getClass());
	}

}
