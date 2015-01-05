package com.cloud.admin.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.admin.dao.PassengerDAO;
import com.cloud.admin.model.Passenger;
import com.cloud.admin.tranfer.PassengerTransfer;


@Component
@Path("/passenger")
public class PassengerResource {
	
	@Autowired
	PassengerDAO passengerDAO;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public PassengerTransfer get(@QueryParam("lastname") String lastName, @QueryParam("reservationnumber") String resvNumber,  @QueryParam("flightnumber") String flightNumber){
		
		Passenger psg = passengerDAO.get(lastName, resvNumber, flightNumber);
		
		return new PassengerTransfer(psg.getFirstName(), psg.getLastName(), psg.getReserNumber(), psg.getFlightFligNumber(), psg.getFlightFligDate(), psg.getStatus().toString());
		
	}
	
	

}
