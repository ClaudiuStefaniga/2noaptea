package mobile.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import mobile.server.model.Event;
import mobile.server.model.Urgency;
import mobile.server.model.User;
import mobile.server.repository.EventRepository;
import mobile.server.repository.UrgencyRepository;
import mobile.server.repository.UserRepository;
import mobile.server.service.AndroidPushNotificationsService;

@RestController
public class UrgencyController {
	
	@Autowired
	UrgencyRepository urgencyRepository;
	
	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	
	
	@RequestMapping(value = "/getAllUserUrgencies/{userId}", method = RequestMethod.GET)
	public List<Urgency> getUserUrgencies(@PathVariable int userId) {
		List<Urgency> urgencies = urgencyRepository.findAllUrgenciesByUserId(userId);
		return urgencies;
	}
	
	@RequestMapping(value = "/getAllUrgencies", method = RequestMethod.GET)
	public List<Urgency> getAllUrgencies() {
		Iterable<Urgency> urgencies = urgencyRepository.findAll();
		List<Urgency> listOfUrgencies = new ArrayList<Urgency>();
		
		for(Urgency urgency: urgencies) {
			listOfUrgencies.add(urgency);
		}
		
		return listOfUrgencies;
	}
	
	@RequestMapping(value = "/deleteUrgency", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestBody Integer id) {
		//user.setId(0);
		urgencyRepository.delete(id.intValue());
		return "Succes";
	}
	
	@RequestMapping(value = "/postUrgency", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> postUrgency(@RequestBody Urgency urgency) throws JSONException { 
		
		urgencyRepository.save(urgency);
		
		JSONObject body = new JSONObject();
		
		//get users events
		User urgencyUser = userRepository.findOne(urgency.getUser().getId());
		String urgencyEvent = urgency.getUrgencyType();
		
		Iterable<Event> allEvents = eventRepository.findAll();
		
		for(Event event : allEvents) {
			if(urgency.getUrgencyType().equals(event.getEventName())) {
				List<User> allUsers = event.getUsers();
				for(User user : allUsers) {
					
					String TOPIC = Integer.toString(user.getId());
					
					/**
					 * la mesaj o sa ai obiectul tau Json,
					 * dupa topic identifici clientul la care trimiti notificarea,
					 * daca mai multi clienti au acelasi topic, toti vor primi notificarea
					 *  poti folosi si un token ca sa trimiti la un sigur client, dar eu am folosit topicuri
					 */
					
					
					body.put("to", "/topics/" + TOPIC); // 
					body.put("priority", "high");
					
					JSONObject notification = new JSONObject();
					notification.put("title", urgency.getTitle());
					notification.put("body", urgency.getDescription());
					notification.put("click_action", "ACTIVITY_XPTO");
					
					
					JSONObject data = new JSONObject();
					//data.put("Key-1", "Data 1");  // aici poti adauga alte date care le poti lua la receptie cu get
					data.put("urgency", new Gson().toJson(urgency));
			 
					body.put("notification", notification);
					body.put("data", data);
					
					HttpEntity<String> request = new HttpEntity<>(body.toString());
					 
					CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
					CompletableFuture.allOf(pushNotification).join();
				}
				break;
			}
		}
		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.OK);
	}
	
	
	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	
}
