package au.id.lagod.entities;

import java.util.HashSet;
import java.util.Set;

import com.objective.keystone.model.event.Event;
import com.objective.keystone.model.event.EventManager;
import com.objective.keystone.model.folder.AuthoringFolder;
import com.objective.keystone.model.folder.FolderManager;

import au.id.lagod.jersey_poc.services.EventService;
import au.id.lagod.jersey_poc.services.FolderService;

public class EventsDTO extends BaseDTO {
	public Set<EventDTO> events = new HashSet<EventDTO>();
	
	public EventsDTO() {}
	
	public EventsDTO(EventService service, EventManager events) {
		super();
		for (Event f: events) {
			this.events.add(new EventDTO(service, f));
		}
		
		String customerIdf = events.getCustomer().getIdentifier();
		
		_links.put("this", service.getEvents(customerIdf));
		_links.put("parent", service.getCustomerService().getCustomer(customerIdf));

	}

}
