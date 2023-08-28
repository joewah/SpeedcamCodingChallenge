package com.example.speedcam.exceptions;

public class Exceptions {

	public static class FileNotFoundException extends Exception {

		public FileNotFoundException() {
			super("file does not exist on S3!");
		}
	}
		
	public static class FileUploadFailedException extends Exception {
		public FileUploadFailedException() {
			super("upload to S3 failed!");
		}
	}
	
	public static class OwnerNotFoundException extends Exception {
		public OwnerNotFoundException () {
			super("owner not found");
		}
	}
	

}
