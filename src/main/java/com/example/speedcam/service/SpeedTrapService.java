package com.example.speedcam.service;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;

import com.example.billing.api.model.BillingDTO;
import com.example.billing.api.model.SpeedingIncidentDTO;
import com.example.speedcam.exceptions.Exceptions.FileUploadFailedException;
import com.example.speedcam.filestorage.service.FileStorageService;


import reactor.core.publisher.Mono;


/**
 * receives the license plate number, the speed limit, the actual speed limit and the photo taken
 * stores photo on s3 and forwards the other information to the billing service
 * @author wahljo1
 *
 */
@Service
public class SpeedTrapService {

	private final WebClient webClient;
	private final FileStorageService fileService;
	
	@Autowired
	public SpeedTrapService(WebClient webClient, FileStorageService fileService) {
		this.webClient = webClient;
		this.fileService = fileService;
	}
	
	public Mono<Long> resolveOwner(String registryNo) {
		Mono<Long> mono = webClient.get()
        .uri("/licenseplate/{registryNo}",registryNo)
        .retrieve()
        .bodyToMono(Long.class);
		return mono;
	}
	
	public String upload(MultipartFile file) throws FileUploadFailedException {
		
			try {
				return fileService.uploadFile(file.getName(), file.getInputStream());
			} catch (FileUploadFailedException | IOException e) {
				// TODO Auto-generated catch block
				throw new FileUploadFailedException();
			}

	}
	
	public Mono<BillingDTO> submitRequestToBilling(SpeedingIncidentDTO speedingIncidentDTO) {
		return webClient.post()
        .uri("/billing}")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(speedingIncidentDTO)
        .retrieve().bodyToMono(BillingDTO.class);
	}
	

	
	
	
}
