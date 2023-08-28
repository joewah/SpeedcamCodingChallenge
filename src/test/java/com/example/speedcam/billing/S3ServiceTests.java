package com.example.speedcam.billing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.speedcam.config.AppConfiguration;
import com.example.speedcam.config.S3Configuration;
import com.example.speedcam.exceptions.Exceptions.FileNotFoundException;
import com.example.speedcam.s3.service.S3Service;


@SpringJUnitConfig
public class S3ServiceTests {
	
	@Autowired 
	private S3Service service;
	

	void testS3UploadAndDeletion() throws FileNotFoundException {

		String fileName = "/images/number_plate_1.jpg";

		File file = new File(S3ServiceTests.class.getResource(fileName).getFile());
		service.uploadFile(file.getName(), file);
		assertTrue(service.doesFileExist("number_plate_1.jpg"));
		service.deleteFile("number_plate_1.jpg");
		assertFalse(service.doesFileExist("number_plate_1.jpg"));
	}

}
