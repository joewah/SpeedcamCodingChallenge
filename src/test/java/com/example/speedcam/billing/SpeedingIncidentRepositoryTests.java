package com.example.speedcam.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import com.example.speedcam.entity.OwnerEntity;
import com.example.speedcam.entity.SpeedingIncidentEntity;
import com.example.speedcam.repository.SpeedingIncidentRepository;

@DataJpaTest 
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SpeedingIncidentRepositoryTests {
	
	@Autowired
	SpeedingIncidentRepository speedingRepo;
	
	@MockBean
    private OwnerEntity ownerEntityMock;

	
	@Test
	void testInsertAndRetrieve() {
		when(ownerEntityMock.getId()).thenReturn(1L);

        SpeedingIncidentEntity incident = new SpeedingIncidentEntity();
        incident.setParent(ownerEntityMock);
        incident.setMaxSpeed(120);
        incident.setSpeed(140);
        incident.setFineInCHF(100);
        incident.setPhotoLocation("/path/to/photo.jpg");

        speedingRepo.save(incident);

        SpeedingIncidentEntity savedIncident = speedingRepo.findById(incident.getId()).orElse(null);

        assertEquals(incident.getId(), savedIncident.getId());
        assertEquals(ownerEntityMock.getId(), savedIncident.getParent().getId());
        assertEquals(incident.getMaxSpeed(), savedIncident.getMaxSpeed());
        assertEquals(incident.getSpeed(), savedIncident.getSpeed());
        assertEquals(incident.getFineInCHF(), savedIncident.getFineInCHF());
        assertEquals(incident.getPhotoLocation(), savedIncident.getPhotoLocation());
	}
}
