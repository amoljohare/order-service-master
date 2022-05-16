package com.hdfc.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hdfc.api.model.CallRecordsModel;

public interface CallRecordsService {
	String getFilePath(MultipartFile docfile) throws IOException;

	boolean putFileOnS3(String filePath, String bucketName, String s3Location);

	boolean saveMetafileRecordsIntoDB(String filePath, String uploadedBy, String bucketName);

	List<String> getRecordFilePaths(List<MultipartFile> docfile) throws IOException;

	boolean putFilesOnS3(List<String> filePaths, String bucketName, String s3Location);

	boolean saveRecordingfileRecordsIntoDB(List<String> filePaths, String uploadedBy, String bucketName,
			List<String> s3PathRecordingKey);

	List<CallRecordsModel> findAll();

	boolean processRecordFile(String category, String s3Location, String uploadedBy, List<MultipartFile> docfile,
			String bucketName);

	List<String> putRecordFilesOnS3(List<String> filePaths, String bucketName, String s3Location);

	List<CallRecordsModel> fetchMetaFileByUser(String userId) throws Exception;
}
