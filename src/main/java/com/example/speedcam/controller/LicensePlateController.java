package com.example.speedcam.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.billing.api.LicenseplateApi;
import com.example.billing.api.model.Okay;
import com.example.billing.api.model.OwnerDTO;
import com.example.billing.api.model.OwnerID;
import com.example.billing.api.model.OwnerIdDTO;
import com.example.speedcam.exceptions.Exceptions.OwnerNotFoundException;
import com.example.speedcam.service.LicensePlateService;

/**
 * 
 * @author wahljo1
 *
 */

@RestController
public class LicensePlateController implements LicenseplateApi {
	
	private final LicensePlateService licensePlateService;
	
	@Autowired
	public LicensePlateController(LicensePlateService licensePlateService) {
		this.licensePlateService = licensePlateService;
	}

	@Override
	public ResponseEntity<OwnerIdDTO> getOwnerByRegistryNo(String registryNo) {
		ResponseEntity<OwnerIdDTO> response = null;
		try {
			long id = licensePlateService.findByRegistryNo(registryNo).getId();
			OwnerIdDTO ownerID = new OwnerIdDTO();
			ownerID.setId(id);
			response = new ResponseEntity<>(ownerID,HttpStatus.OK);
			
		} catch (OwnerNotFoundException e) {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ResponseEntity<OwnerIdDTO> createOwner(@Valid OwnerDTO ownerDTO) {
		ResponseEntity<OwnerIdDTO> response = null;
		long id = licensePlateService.createOwner(ownerDTO);
		OwnerIdDTO ownerID = new OwnerIdDTO();
		ownerID.setId(id);
		response = new ResponseEntity<>(ownerID,HttpStatus.OK);
		return response;
	}
	
	

	
}
