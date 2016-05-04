package com.speakwrite.console;

import java.io.FileNotFoundException;
import com.speakwrite.api.Client;
import com.speakwrite.api.CompletedJobsRequest;
import com.speakwrite.api.CompletedJobsResponse;
import com.speakwrite.api.JobDownloadRequest;
import com.speakwrite.api.JobDownloadResponse;
import com.speakwrite.api.JobUploadRequest;
import com.speakwrite.api.JobUploadResponse;
import com.speakwrite.api.JobDownloadRequest.DownloadType;

public class Console {

	public static void main(String[] args) throws Exception {
		System.out.println("start");
		if(args.length < 1) {
			System.out.println("Usage sw upload... | sw completed... | sw download ...");
			return;
		}

		try {
			String action = args[0].toLowerCase();
			switch(action) {
				case "upload": Upload(args); break;
				case "completed": Completed(args); break;
				case "download": Download(args); break;
				default:
					System.out.println(String.format("Unrecognized action: '%s'",action));
					break;
			}
		} catch(Exception ex) {
			System.out.print(ex.getMessage());
		}
		System.out.println("end");
	}

	private static void Download(String[] args) throws Exception {
		if(args.length < 5) {
			System.out.println("Usage: sw download <account_number> <pin> <filename> <destination_file_path>");
			return;
		}
		// simple download request
		JobDownloadRequest request = new JobDownloadRequest();
		// your SpeakWrite account number
		request.accountNumber = args[1];
		// your SpeakWrite account PIN
		request.pin = args[2];
		// the SpeakWrite filename of the job to download
		request.fileName = args[3];
		// download type is the completed transcription document (this is default)
		request.type = DownloadType.Document;
		// where should the download file be saved?
		request.destinationFileName = args[4];
		Client client = new Client();
		// issue request
		JobDownloadResponse response = client.download(request);
		if(response.success) {
			System.out.println(String.format("Successfully downloaded %s to %s", request.fileName, request.destinationFileName));
		}
	}

	private static void Completed(String[] args) throws Exception{
		if(args.length < 3) {
			System.out.println("Usage: sw completed <account_number> <pin>");
			return;
		}
		Client client = new Client();
		CompletedJobsRequest request = new CompletedJobsRequest();
		// your SpeakWrite account number
		request.accountNumber = args[1];
		// your SpeakWrite account PIN
		request.pin = args[2];
		// issue request
		CompletedJobsResponse response = client.getCompletedJobs(request);
		// write success to console, generally you'd want to do something more interesting with the results
		if(response.success) {
			System.out.println(String.format("Successfully retrieved %d completed jobs", response.jobs.size()));
		}
	}

	private static void Upload(String[] args) throws Exception {
		if(args.length < 4) {
			System.out.println("Usage: sw upload <account_number> <pin> <path_to_file> <custom_file_name>(optional) <is_group_conversation>(optional)");
			return;
		}
		JobUploadRequest request = new JobUploadRequest();
		// your SpeakWrite account number
		request.accountNumber = args[1];
		// your SpeakWrite account PIN
		request.pin = args[2];
		// File object pointing to audio file to upload
		request.audioFile = new java.io.File(args[3]);
		// currently there is little argument checking within the SPeakWrite.API library
		if(!request.audioFile.exists()) {
			throw new FileNotFoundException("Must provide path to valid audio file");
		}
		// optional, custom file name which SpeakWrite will track and return with a call to "GetCompleteJobs" so you
		// can use an internal tracking number
		if(args.length > 4) {
			request.customFileName = args[4];
		}
		// optional, True for conversation with more than one speaker, defaults to False
		if(args.length > 5) {
			request.isGroupConversation = Boolean.parseBoolean(args[5]);
		}
		Client client = new Client();
		// issue request
		JobUploadResponse response = client.uploadJob(request);
		if(response.success) {
			System.out.println("Successfully created job with filename " + response.fileName);
		}
	}
}
