package com.example.speedcam.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
/**
 * 
 * @author wahljo1
 *
 */
@Configuration
public class AppConfiguration {
	
    @Value("${general.url}")
    private String url;
	
	@Bean
	public ModelMapper modelMapper() {
		 return new ModelMapper();
	}
	
	@Bean 
	public WebClient webClient() {
		return WebClient.builder().baseUrl(url).build();
	}


}
