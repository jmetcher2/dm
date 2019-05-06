package au.id.lagod.entities;

import com.objective.keystone.model.event.Event;

import au.id.lagod.jersey_poc.services.EventService;

public class EventDTO extends BaseDTO {
	
	public String shortName;
	public Long id;
	public String eventType;
	
	public EventDTO() {}

	public EventDTO (EventService service, Event event) {
		super();

		this.shortName = event.getShortName();
		this.id = event.getId();
		this.eventType = event.getType().toString();

		String customerName = event.getCustomer().getIdentifier();
		
		_links.put("self", service.getEvent(customerName, shortName));
		_links.put("parent", service.getEvents(customerName));
	}


}
