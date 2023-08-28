package com.example.speedcam.billing;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.example.speedcam.config.AppConfiguration;
import com.example.speedcam.entity.OwnerEntity;
import com.example.speedcam.repository.OwnerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest 
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OwnerRepositoryTests {
	
	@Autowired
	private OwnerRepository ownerRepository;

	
	@Test
	void testInsertAndRetrieve() {
		OwnerEntity owner = new OwnerEntity();
		owner.setCity("Springfield");
		owner.setName("Marge");
		owner.setSurname("Simpson");
		owner.setPostalCode(12345);
		owner.setStreet("Fake Street");
		owner.setNumber(123);
		owner.setRegistryNo("SP45678");
		owner = ownerRepository.saveAndFlush(owner);
		Optional<OwnerEntity> owner2 = ownerRepository.findById(owner.getId());
		assertEquals(owner2.get().getName(),owner.getName());
		
	}

}
