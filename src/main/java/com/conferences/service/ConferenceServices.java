package com.conferences.service;
import java.time.LocalDateTime;
import javafx.scene.control.Alert;

import com.conferences.DAO.RegistrationsDAO;
import com.conferences.model.Registration;

import java.sql.SQLException;
import java.util.List;


public class ConferenceServices {
	public String titleValidation(String title) {
		if(title == null || title.trim().isEmpty()) {
			 return "Please enter a valid title";
		}else {
			return null;
		}
	}
	
	public String capacityValidation(String capacityText) {
		if(capacityText == null || capacityText.trim().isEmpty()){
			return "Please enter a capacity";
		}

		try{
			 int cap = Integer.parseInt(capacityText.trim());
			  if(cap <= 0) {
				return "Please make sure capacity is positive";
			  }
		}catch(NumberFormatException e){
			return "Capacity must be a valid whole number";
		}

		return null;
	}

	public String capacityUpdateValidation(int conferenceID, int newCapacity) throws SQLException{
		RegistrationsDAO db = new RegistrationsDAO();
		List<Registration> registrations = db.getRegistrationsByConference(conferenceID);
		int confirmed = 0;
		for(Registration r : registrations){
			if(r.getStatus().equals("CONFIRMED")){
				confirmed++;
			}
		}
		if(newCapacity < confirmed){
			return "Capacity cannot be less than the number of confirmed participants ("+ confirmed + ").";
		}
		return null;
	}

	public String dateValidation(LocalDateTime start, LocalDateTime end){
		if(start == null || end == null){
			return "Please select the start and end times";
		}
		if(!end.isAfter(start)){
			return "End date and time must be after the start date and time";
		}

		return null;
	}

	public String deleteConferenceValidation(int conferenceID) throws SQLException{
		RegistrationsDAO db = new RegistrationsDAO();
		List<Registration> registrations = db.getRegistrationsByConference(conferenceID);
		for(Registration r : registrations){
			if(r.getStatus().equals("CONFIRMED") || r.getStatus().equals("WAITLISTED")){
				return "Cannot delete a conference with confirmed or waitlisted participants";
			}
		}
		return null;
	}
	public void alertMessage(String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("Conference Status");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
}

