package mobile.server.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mobile.server.model.Event;
import mobile.server.model.Urgency;
import mobile.server.model.User;
import mobile.server.repository.EventRepository;
import mobile.server.repository.UrgencyRepository;
import mobile.server.repository.UserRepository;

@RestController
public class HiController {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	UrgencyRepository urgencyRepository;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping("/hi")
	public String sayHi() {
		return "hi";
	}
	
	@RequestMapping("/populateEvents")
	public String populateEvents() {
		eventRepository.save(new Event("Event1"));
		eventRepository.save(new Event("Event2"));
		eventRepository.save(new Event("Event3"));
		eventRepository.save(new Event("Event4"));
		return "Evenimente salvate";
	}
	
	@RequestMapping(value = "/populateDatabase")
	public void populateDatabase() {
		Urgency urgency = new Urgency();
        urgency.setTitle("Urgenta 1");
        urgency.setDescription("Descriere 1");
        urgency.setLatitude(45.744500);
        urgency.setLongitude(21.232800);
        urgencyRepository.save(urgency);

        urgency = new Urgency();
        urgency.setTitle("Urgenta 2");
        urgency.setDescription("Descriere 2");
        urgency.setLatitude(45.784300);
        urgency.setLongitude(21.262200);
        urgencyRepository.save(urgency);

        urgency = new Urgency();
        urgency.setTitle("Urgenta 3");
        urgency.setDescription("Descriere 3");
        urgency.setLatitude(45.704400);
        urgency.setLongitude(21.292300);
        urgencyRepository.save(urgency);
	}
	
	@RequestMapping(value = "/deleteUser")
	public void deleteUser() {
		User toDeleteUser = userRepository.findOne(2);
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	}
	

}
