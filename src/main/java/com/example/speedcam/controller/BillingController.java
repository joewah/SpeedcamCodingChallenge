package com.example.speedcam.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.billing.api.BillingApi;
import com.example.billing.api.model.BillingDTO;
import com.example.billing.api.model.SpeedingIncidentDTO;
import com.example.speedcam.exceptions.Exceptions.OwnerNotFoundException;
import com.example.speedcam.service.BillingService;

/**
 * 
 * @author wahljo1
 *
 */


@RestController
public class BillingController implements BillingApi {
	
	private final BillingService billingService;
	
	@Autowired
	public BillingController(BillingService billingService) {
		this.billingService = billingService;
	}

	@Override
	public ResponseEntity<BillingDTO> billingProcess(@Valid SpeedingIncidentDTO speedingIncidentDTO) {
		ResponseEntity<BillingDTO> response = null;
		try {
			BillingDTO billingDTO = billingService.processBilling(speedingIncidentDTO);
			response = new ResponseEntity<>(billingDTO,HttpStatus.OK);
		} catch (OwnerNotFoundException e) {
			// TODO Auto-generated catch block
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	

}
