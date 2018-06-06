package mobile.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mobile.server.model.Event;
import mobile.server.repository.EventRepository;
import mobile.server.repository.UserRepository;

@RestController
public class EventsController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	

	@RequestMapping(value = "/getNotifiableEvents", method = RequestMethod.GET)
	public List<Event> getNotifiableEvents() {
		List<Event> events = new ArrayList<Event>();
		for(Event event : eventRepository.findAll()) {
			events.add(event);
		}
		return events;
	}

}
