package com.conferences.service;
import java.time.LocalDateTime;
import javafx.scene.control.Alert;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import com.conferences.DAO.UserDAO;
import com.conferences.DAO.RegistrationsDAO;
import com.conferences.model.User;
import com.conferences.model.Registration;
import java.util.Comparator;
import com.conferences.DAO.ConferencesDAO;
import com.conferences.model.Conference;
import com.conferences.model.LocalizationManager;
import java.text.MessageFormat;

public class ConferenceServices {

	public String titleValidation(String title) {
		if(title == null || title.trim().isEmpty()) {
			 return LocalizationManager.get("validation.title.invalid");
		}else {
			return null;
		}
	}
	
	public String capacityValidation(String capacityText) {
		if(capacityText == null || capacityText.trim().isEmpty()){
			 return LocalizationManager.get("validation.capacity.empty");
		}
		try{
			 int cap = Integer.parseInt(capacityText.trim());
			  if(cap <= 0) {
			 return LocalizationManager.get("validation.capacity.positive");
			  }
		}catch(NumberFormatException e){
			 return LocalizationManager.get("validation.capacity.invalid");
		}
		return null;
	}

	public String capacityUpdateValidation(int conferenceID, int newCapacity) throws SQLException{
		int confirmed = getConfirmedCount(conferenceID);
		if(newCapacity < confirmed){
			return MessageFormat.format(LocalizationManager.get("validation.capacity.minimum"), confirmed);
		}
		return null;
	}

	public String dateValidation(LocalDateTime start, LocalDateTime end){
		if(start == null || end == null){
			 return LocalizationManager.get("validation.date.empty");
		}
		if(!end.isAfter(start)){
			 return LocalizationManager.get("validation.date.invalid");
		}
		return null;
	}

	public String deleteConferenceValidation(int conferenceID) throws SQLException{
		RegistrationsDAO db = new RegistrationsDAO();
		List<Registration> registrations = db.getRegistrationsByConference(conferenceID);
		for(Registration r : registrations){
			if(r.getStatus().equals("CONFIRMED") || r.getStatus().equals("WAITLISTED")){
			 return LocalizationManager.get("validation.delete.registered");
			}
		}
		return null;
	}

	public int getConfirmedCount(int conferenceID) throws SQLException{
		RegistrationsDAO db = new RegistrationsDAO();
		List<Registration> registrations = db.getRegistrationsByConference(conferenceID);
		int confirmed = 0;
		for(Registration r : registrations){
			if(r.getStatus().equals("CONFIRMED")){
				confirmed++;
			}
		}
		return confirmed;
	}

	public int getWaitlistedCount(int conferenceID) throws SQLException{
		RegistrationsDAO db = new RegistrationsDAO();
		List<Registration> registrations = db.getRegistrationsByConference(conferenceID);
		int waitlisted = 0;
		for(Registration r : registrations){
			if(r.getStatus().equals("WAITLISTED")){
				waitlisted++;
			}
		}
		return waitlisted;
	}

	public boolean isConferenceFull(int conferenceID, int capacity) throws SQLException{
		int available = getAvailableSeats(conferenceID, capacity);
		if(available <= 0){
			return true;
		}
		return false;
	}

	public int getAvailableSeats(int conferenceID, int capacity) throws SQLException{
		int confirmed = getConfirmedCount(conferenceID);
		int available = capacity - confirmed;
		return available;
	}

	public void alertMessage(String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(LocalizationManager.get("alert.conference.title"));
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public List<User> getConfirmedParticipants(int conferenceId) throws SQLException{
		RegistrationsDAO db = new RegistrationsDAO();
		UserDAO user = new UserDAO();
		List<Registration> registrations = db.getRegistrationsByConference(conferenceId);
		List<Registration> confirmed = registrations.stream()
											.filter(r -> r.getStatus().equals("CONFIRMED"))
											.toList();
		List<User> participants = new ArrayList<>();
		for(Registration r : confirmed){
			User u = user.getUserById(r.getUserId());
			if(u != null){
				participants.add(u);
			}
		}
		return participants;
	}

	public List<User> getWaitlistedParticipants(int conferenceId) throws SQLException{
		RegistrationsDAO db = new RegistrationsDAO();
		UserDAO user = new UserDAO();
		List<Registration> registrations = db.getRegistrationsByConference(conferenceId);
		List<Registration> confirmed = registrations.stream()
											.filter(r -> r.getStatus().equals("WAITLISTED"))
											.sorted(Comparator.comparing(Registration::getWaitlistPosition))
											.toList();
		List<User> participants = new ArrayList<>();
		for(Registration r : confirmed){
			User u = user.getUserById(r.getUserId());

			if(u != null){
				participants.add(u);
			}
		}
		return participants;
	}

	public List<Registration> getParticipantRegistrations(int userId) throws SQLException{
		RegistrationsDAO db = new RegistrationsDAO();
		List<Registration> registrations = db.getRegistrationsByUser(userId);
		List<Registration> activeRegistrations = registrations.stream()
											.filter(r -> r.getStatus().equals("WAITLISTED") || r.getStatus().equals("CONFIRMED"))
											.toList();
		return activeRegistrations;
	}

	public List<Conference> getUnregisteredConference() throws SQLException{
		ConferencesDAO db = new ConferencesDAO();
		List<Conference> conferences = db.getAllConferences();
		List<Conference> unregistered = new ArrayList<>();
		for(Conference c : conferences){
			int id = c.getConferenceID();
			if(getConfirmedCount(id) == 0 && getWaitlistedCount(id)==0){
				unregistered.add(c);
			}
		}
		return unregistered;
	}
}

