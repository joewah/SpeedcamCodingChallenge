package com.example.speedcam.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.billing.api.SpeedtrapApi;
import com.example.billing.api.model.BillingDTO;
import com.example.billing.api.model.Okay;
import com.example.billing.api.model.SpeedingIncidentDTO;
import com.example.speedcam.exceptions.Exceptions.FileUploadFailedException;
import com.example.speedcam.filestorage.service.FileStorageService;
import com.example.speedcam.service.SpeedTrapService;

/**
 * 
 * @author wahljo1
 *
 */
@RestController
public class SpeedTrapController implements SpeedtrapApi {
	
	private final FileStorageService storageService;
	private final SpeedTrapService speedTrapService;
	
	@Autowired
	public SpeedTrapController(FileStorageService storageService, SpeedTrapService speedTrapService) {
		this.storageService = storageService;
		this.speedTrapService = speedTrapService;
	}

	@Override
	public ResponseEntity<BillingDTO> processIncident(MultipartFile file, @Valid String registryNo, @Valid Integer maxSpeed,
			@Valid Integer actualSpeed) {
		SpeedingIncidentDTO speedingDTO = new SpeedingIncidentDTO();
		ResponseEntity<BillingDTO> response = null;
		try {
			String fileLocation = storageService.uploadFile(file.getName(), file.getInputStream());
			Long ownerID = speedTrapService.resolveOwner(registryNo).block();
			speedTrapService.resolveOwner(registryNo); 
			speedingDTO.setActualSpeed(actualSpeed);
			speedingDTO.setMaxSpeed(maxSpeed);
			speedingDTO.setPhotoLocation(fileLocation);
			speedingDTO.setOwnerID(ownerID);
			BillingDTO billingDTO = speedTrapService.submitRequestToBilling(speedingDTO).block();
			response = new ResponseEntity<>(billingDTO,HttpStatus.CREATED);
		} catch (FileUploadFailedException | IOException e) {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	

}
