package com.example.speedcam.filestorage.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.example.speedcam.exceptions.Exceptions.FileNotFoundException;
import com.example.speedcam.exceptions.Exceptions.FileUploadFailedException;



@Service
public interface FileStorageService {
	
	public String uploadFile( String keyName, File file) throws FileUploadFailedException;
	public String uploadFile(String keyName, InputStream inputStream) throws FileUploadFailedException;
	public void deleteFile(String keyName) throws FileNotFoundException;
	public boolean doesFileExist(String keyName);
}
