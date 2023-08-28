package com.example.speedcam.config;

import org.apache.catalina.authenticator.BasicAuthenticator.BasicCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * 
 * @author wahljo1
 *
 */
@Configuration
public class S3Configuration {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKeyId;

    @Value("${cloud.aws.credentials.secretKey}")
    private String accessKeySecret;

    @Value("${cloud.aws.region.static}")
    private String region;


    
    @Bean
    public S3Client s3() {
    	StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, accessKeySecret));
    	return S3Client.builder().region(Region.of(region)).credentialsProvider(credentialsProvider).build();
    }
	

}
