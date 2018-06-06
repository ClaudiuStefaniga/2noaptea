package mobile.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mobile.server.model.User;
import mobile.server.repository.EventRepository;
import mobile.server.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EventRepository eventRepository;

	@RequestMapping(value = "/login/{userEmail}/{userPassword}", method = RequestMethod.GET)
	public User login(@PathVariable String userEmail, @PathVariable String userPassword) {
		User user = userRepository.findByEmail(userEmail);
		if (user != null) {
			if(!user.getPassword().equals(userPassword)) {
				return null;
			}
		}
		return user;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestBody User user) {
		//user.setId(0);
		userRepository.save(user);
		return "Succes";
	}
	
	@RequestMapping(value = "/updateEvents", method = RequestMethod.POST)
	public @ResponseBody String updateEvents(@RequestBody User user) {
		userRepository.save(user);
		return "Succes";
	}
	
	
	@RequestMapping(value = "/postLocation", method = RequestMethod.POST)
	public @ResponseBody String postLocation(@RequestBody User user) {
		User userFromDB = userRepository.findByEmail(user.getEmail());
		if (userFromDB != null) {
			userFromDB.setLatitude(user.getLatitude());
			userFromDB.setLongitude(user.getLongitude());
		}
		userRepository.save(userFromDB);
		return "Succes";
	}
}
