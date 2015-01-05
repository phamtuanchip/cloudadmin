package com.cloud.admin.model;

import java.io.Serializable;
import java.util.Date;

public class Passenger implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 8129761719956049010L;

private static final String BLANK = " ";
	
	private String firstName;
	
	private String lastName;
	
	private String reserNumber;
	
	private String gender;
	
	private int numberBags;
	
	private int bagsWeight;
	
	private String addInfor;
	
	private String boardNumber;
	
	private String seatNumber;
	
	private String status;
	
	private String flightFligNumber;
	
	private Date flightFligDate;

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

	public String getReserNumber() {
		return reserNumber;
	}

	public void setReserNumber(String reserNumber) {
		this.reserNumber = reserNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getNumberBags() {
		return numberBags;
	}

	public void setNumberBags(int numberBags) {
		this.numberBags = numberBags;
	}

	public int getBagsWeight() {
		return bagsWeight;
	}

	public void setBagsWeight(int bagsWeight) {
		this.bagsWeight = bagsWeight;
	}

	public String getAddInfor() {
		return addInfor;
	}

	public void setAddInfor(String addInfor) {
		this.addInfor = addInfor;
	}

	public String getBoardNumber() {
		return boardNumber;
	}

	public void setBoardNumber(String boardNumber) {
		this.boardNumber = boardNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlightFligNumber() {
		return flightFligNumber;
	}

	public void setFlightFligNumber(String flightFligNumber) {
		this.flightFligNumber = flightFligNumber;
	}

	public Date getFlightFligDate() {
		return flightFligDate;
	}

	public void setFlightFligDate(Date flightFligDate) {
		this.flightFligDate = flightFligDate;
	}

	public static String getBlank() {
		return BLANK;
	}

	@Override
	public String toString() {
		return "Passenger [firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", flightFligNumber="
				+ flightFligNumber + ", flightFligDate=" + flightFligDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((reserNumber == null) ? 0 : reserNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passenger other = (Passenger) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (reserNumber == null) {
			if (other.reserNumber != null)
				return false;
		} else if (!reserNumber.equals(other.reserNumber))
			return false;
		return true;
	}
	
	

}
