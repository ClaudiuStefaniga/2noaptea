package mobile.server.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private int id;
	private String name;
	private String email;
	private String password;
	private double latitude;
	private double longitude;
	
	@JsonIgnoreProperties("user")
	@OneToMany(mappedBy = "user")
	private List<Urgency> urgencies;
	
	@JsonIgnoreProperties("users")
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "events_users",
            joinColumns = @JoinColumn(name = "userd_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
	private List<Event> events = new ArrayList<>();

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addEvent(Event event) {
		this.events.add(event);
	}

	public List<Urgency> getUrgencies() {
		return urgencies;
	}

	public void setUrgencies(List<Urgency> urgencies) {
		this.urgencies = urgencies;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
