package com.cloud.admin.tranfer;

import java.util.Date;

public class PassengerTransfer {
	
	private String firstName;
	private String lastName;
	private String resvNumber;
	private String flightNumber;
	private Date   flightDate;
	private String status;
	public PassengerTransfer(){}
	public PassengerTransfer(String firstName, String lastName,
			String resvNumber, String flightNumber, Date flightDate,
			String i) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.resvNumber = resvNumber;
		this.flightNumber = flightNumber;
		this.flightDate = flightDate;
		this.status = i;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getResvNumber() {
		return resvNumber;
	}
	public void setResvNumber(String resvNumber) {
		this.resvNumber = resvNumber;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public Date getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
