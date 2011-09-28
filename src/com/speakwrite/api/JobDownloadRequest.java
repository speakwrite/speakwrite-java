package com.speakwrite.api;

public class JobDownloadRequest extends BaseApiRequest {
	public String fileName;
	public String destinationFileName;
	public DownloadType type;
	public String customFileName;
	
	public enum DownloadType {
		Document,
		SourceAudio
	}
}
