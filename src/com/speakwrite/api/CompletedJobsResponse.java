package com.speakwrite.api;

import java.util.ArrayList;
import java.util.List;

public class CompletedJobsResponse extends BaseApiResponse {
	public CompletedJobsResponse() {
		jobs = new ArrayList<CompletedJob>();
	}
	
	public List <CompletedJob> jobs;
}
