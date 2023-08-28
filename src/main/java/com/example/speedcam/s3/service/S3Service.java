package com.example.speedcam.s3.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.speedcam.exceptions.Exceptions;
import com.example.speedcam.exceptions.Exceptions.FileNotFoundException;
import com.example.speedcam.exceptions.Exceptions.FileUploadFailedException;
import com.example.speedcam.filestorage.service.FileStorageService;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 * 
 * @author wahljo1
 *
 */

@Service
public class S3Service implements FileStorageService  {
		
	@Value("${general.aws.s3bucket}")
	private String bucketName;
	
	private final S3Client s3;

	
	@Autowired
	public S3Service(S3Client s3) {
		this.s3 = s3;
	}
	
	public String uploadFile( String keyName, File file) {
		PutObjectRequest putRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();
	    s3.putObject(putRequest,Paths.get(file.getAbsolutePath())).sdkHttpResponse().isSuccessful();
	    String s3Url = "https://" + bucketName + ".s3.amazonaws.com/" + keyName;
	    return s3Url;
	}
	
	
	
	public String uploadFile(String keyName, InputStream inputStream) throws FileUploadFailedException {
		PutObjectRequest putRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();
		try {
			s3.putObject(putRequest,
			            RequestBody.fromInputStream(inputStream, inputStream.available()));
		} catch (AwsServiceException | SdkClientException | IOException e) {
			throw new FileUploadFailedException();
		}
	    String s3Url = "https://" + bucketName + ".s3.amazonaws.com/" + keyName;
	    return s3Url;

	}
	
	public void deleteFile(String keyName) throws FileNotFoundException {
		 DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
	                .bucket(bucketName)
	                .key(keyName)
	                .build();

	     DeleteObjectResponse deleteObjectResponse = s3.deleteObject(deleteObjectRequest);
	     if(!deleteObjectResponse.sdkHttpResponse().isSuccessful())
	    	 throw new FileNotFoundException();
	    	 
	}

	@Override
	public boolean doesFileExist(String keyName)  {
		boolean exists = false;
		HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
			    .bucket(bucketName)
			    .key(keyName)
			    .build();
			try {
				HeadObjectResponse headObjectResponse = s3.headObject(headObjectRequest);
				exists = headObjectResponse.sdkHttpResponse().isSuccessful();
			}
			
			catch(NoSuchKeyException e) {}
			return exists;
	}
	



}
