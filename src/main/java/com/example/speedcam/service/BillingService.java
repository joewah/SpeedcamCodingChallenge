package com.example.speedcam.service;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.billing.api.model.BillingDTO;
import com.example.billing.api.model.SpeedingIncidentDTO;
import com.example.speedcam.domain.Billing;
import com.example.speedcam.entity.OwnerEntity;
import com.example.speedcam.entity.SpeedingIncidentEntity;
import com.example.speedcam.exceptions.Exceptions.OwnerNotFoundException;
import com.example.speedcam.repository.SpeedingIncidentRepository;

/**
 * receives data from a speeding incident, calculates the fine and stores all relevant information to the database
 * returns a BillingDTO with relevant billing information
 * @author wahljo1
 *
 */

@Service
public class BillingService {
	
	private final SpeedingIncidentRepository speedingRepo;
	private final EntityManager entityManager;
	
	@Autowired
	public BillingService(SpeedingIncidentRepository speedingRepo, ModelMapper modelMapper,
			EntityManager entityManager) {
		this.speedingRepo = speedingRepo;
		this.modelMapper = modelMapper;
		this.entityManager = entityManager;
	
	}
	
	public BillingDTO processBilling(SpeedingIncidentDTO speedingIncident) throws OwnerNotFoundException {
		long ownerID = speedingIncident.getOwnerID();
		int maxSpeed = speedingIncident.getMaxSpeed();
		int actualSpeed = speedingIncident.getActualSpeed();
		OwnerEntity owner = entityManager.getReference(OwnerEntity.class, ownerID);
		SpeedingIncidentEntity speedingEntity = new SpeedingIncidentEntity();
		speedingEntity.setParent(owner);
		speedingEntity.setMaxSpeed(maxSpeed);
		speedingEntity.setSpeed(actualSpeed);
		speedingEntity.setPhotoLocation(speedingIncident.getPhotoLocation());
		int fine = Billing.calculateFineInChf(maxSpeed, actualSpeed);
		speedingEntity.setFineInCHF(fine);
		//create DB entry with owner, max speed, actual speed, photo location and fine
		speedingRepo.saveAndFlush(speedingEntity);
		// create return DTO
		BillingDTO billingDTO = new BillingDTO();
		billingDTO.setFineInChf(fine);
		billingDTO.setOwner(ownerID);
		
		return billingDTO;
		
	}

}
