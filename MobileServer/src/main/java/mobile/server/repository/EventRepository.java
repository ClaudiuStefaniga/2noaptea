package mobile.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mobile.server.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer>{
	public Event findByEventName(String eventName);
}
