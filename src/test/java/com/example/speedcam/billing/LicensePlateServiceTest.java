package com.example.speedcam.billing;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.speedcam.entity.OwnerEntity;
import com.example.speedcam.exceptions.Exceptions.OwnerNotFoundException;
import com.example.speedcam.repository.OwnerRepository;
import com.example.speedcam.service.LicensePlateService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.assertj.core.util.Arrays;

@SpringBootTest
public class LicensePlateServiceTest {
	
    @InjectMocks
    @Autowired
    private LicensePlateService licensePlateService;

    @MockBean
    private OwnerRepository ownerRepository;
    
    @Test 
    public void testFindOwnerByRegistryNo_userExists() throws OwnerNotFoundException {
    	final String registryNo = "ABC123";
    	final String name = "John Doe";
    	OwnerEntity owner = new OwnerEntity();
        owner.setRegistryNo(registryNo);
        owner.setName(name);
        when(ownerRepository.findByRegistryNo(registryNo)).thenReturn(Collections.singletonList(owner));

        OwnerEntity fetchedOwner = licensePlateService.findByRegistryNo(registryNo);

        // Assert
        assertNotNull(fetchedOwner);
        assertEquals(name, owner.getName());
    }
    
    @Test 
    public void testFindOwnerByRegistryNo_shouldFail()  {
    	final String registryNo = "ABC123";
    	final String registryNo2 = "ABC1234";
    	final String name = "John Doe";
    	OwnerEntity owner = new OwnerEntity();
        owner.setRegistryNo(registryNo);
        owner.setName(name);
        when(ownerRepository.findByRegistryNo(registryNo)).thenReturn(Collections.singletonList(owner));

        assertThrows(OwnerNotFoundException.class, () -> licensePlateService.findByRegistryNo(registryNo2));


    }
	

}
