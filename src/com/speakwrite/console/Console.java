package com.speakwrite.console;

import java.io.File;

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
		Client client = new Client();
		
		System.out.println("Get completed jobs");
		CompletedJobsRequest request = new CompletedJobsRequest();
		//TODO account number & pin
		
		CompletedJobsResponse response = client.getCompletedJobs(request);
		System.out.println(response.success);
		System.out.println(response.message);
		System.out.println(response.jobs.size());
		
		System.out.println("download job");
		
		JobDownloadRequest downloadRequest = new JobDownloadRequest();
		
		
		downloadRequest.fileName = "110831_125227_PH";
		downloadRequest.destinationFileName = "out.doc";
		downloadRequest.type = DownloadType.Document;
		JobDownloadResponse downloadResponse = client.download(downloadRequest);
		
		System.out.println("Upload job");
		JobUploadRequest uploadRequest = new JobUploadRequest();
		
		uploadRequest.audioFile = new File("C:\\go.mp3");
		JobUploadResponse jobUploadResponse = client.uploadJob(uploadRequest);
		System.out.println("Upload finished, new file: " + jobUploadResponse.fileName);
		
		
		System.out.println("end");
	}

}
