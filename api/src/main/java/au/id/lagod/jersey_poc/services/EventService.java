package au.id.lagod.jersey_poc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.objective.keystone.model.event.Event;

import au.id.lagod.entities.EventDTO;
import au.id.lagod.entities.EventsDTO;

@Path("/customers/{customerIdentifier}/events")
public class EventService extends BaseService {
	@GET
	@Path("/")
	public EventsDTO getEvents(@PathParam("customerIdentifier") String customerIdentifier) {
		return new EventsDTO(this, model.customers(customerIdentifier).getEvents());
	}
	
	@GET
	@Path("/{eventName}")
	public EventDTO getEvent(@PathParam("customerIdentifier") String customerIdentifier,
			@PathParam("eventName") String eventName) {
		return getEventDTO(model.customers(customerIdentifier).events(eventName));
	}

	public EventDTO getEventDTO(Event e) {
		return new EventDTO(this, e);
	}

}
