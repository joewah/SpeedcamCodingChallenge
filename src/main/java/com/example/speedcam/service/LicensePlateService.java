package com.example.speedcam.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.billing.api.model.OwnerDTO;
import com.example.speedcam.entity.OwnerEntity;
import com.example.speedcam.exceptions.Exceptions;
import com.example.speedcam.exceptions.Exceptions.OwnerNotFoundException;
import com.example.speedcam.repository.OwnerRepository;

/**
 * responsible for creating new owner entries and resolve owner information by registry numbers
 * @author wahljo1
 *
 */
@Service
public class LicensePlateService {
	
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;
    
    @Autowired
    public LicensePlateService(OwnerRepository ownerRepository, ModelMapper modelMapper) {
    	this.ownerRepository = ownerRepository;
    	this.modelMapper = modelMapper;
    }
    

    
    public OwnerEntity findByRegistryNo(String registryNo) throws OwnerNotFoundException {
    	List<OwnerEntity> entities = ownerRepository.findByRegistryNo(registryNo);
    	OwnerEntity owner = null;
    	if(entities.size()>1) 
    		throw new RuntimeException("registry number should be unique!");
    	if(entities.size()==1) { //owner found
    		owner = entities.get(0);
    	}
    	else if(entities.size()==0)
    		throw new Exceptions.OwnerNotFoundException();

    	return owner;
    }
    

    
    public long createOwner(OwnerDTO ownerDTO) {
    	OwnerEntity entity = modelMapper.map(ownerDTO, OwnerEntity.class);
    	entity = ownerRepository.saveAndFlush(entity);
    	return entity.getId();
    	
    }
	

}
