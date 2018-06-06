package mobile.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mobile.server.model.Urgency;

@Repository
public interface UrgencyRepository extends CrudRepository<Urgency, Integer>{
	public List<Urgency> findAllUrgenciesByUserId(int i);
}
