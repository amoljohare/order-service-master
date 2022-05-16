package com.hdfc.api.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hdfc.api.model.CallRecordsModel;
import com.hdfc.api.repository.CallRecordsRepository;
import com.opencsv.CSVReader;

@Service
public class CallRecordsServiceImpl implements CallRecordsService {
	@Autowired
	CallRecordsRepository callRecordsRepo;

	public boolean putFileOnS3(String filePath, String bucketName, String s3Location) {
		AmazonS3 s3Client = (AmazonS3) ((AmazonS3ClientBuilder) AmazonS3ClientBuilder.standard()
				.withRegion(Regions.AP_SOUTH_1)).build();

		try {
			File convFile = new File(filePath);
			String filepath = s3Location + "/" + filePath;
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Location + "/" + filePath, convFile);
			s3Client.putObject(putObjectRequest);
			URL s3Url = s3Client.getUrl(bucketName, filepath);
			System.out.println(s3Url.toURI());
			System.out.println("Object URl:" + s3Url.toExternalForm());
			return true;
		} catch (Exception var9) {
			var9.printStackTrace();
			return false;
		}
	}

	public String getFilePath(MultipartFile docfile) throws IOException {
		System.out.println("docfile.getOriginalFilename():" + docfile.getOriginalFilename());
		String filePath = docfile.getOriginalFilename();
		File convFile = new File(filePath);
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(docfile.getBytes());
		fos.close();
		return filePath;
	}

	public boolean saveMetafileRecordsIntoDB(String filePath, String uploadedBy, String bucketName) {
		try {
			int count = 0;
			List<CallRecordsModel> callRecordsModelList = new ArrayList();
			new CallRecordsModel();
			FileReader filereader = new FileReader(filePath);
			CSVReader csvReader = new CSVReader(filereader);

			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {
				if (!nextRecord[0].equals("File Path")) {
					++count;
					CallRecordsModel callRecordsModel = new CallRecordsModel();
					callRecordsModel.setRecordingFileName(nextRecord[0]);
					callRecordsModel.setUserId(nextRecord[1]);
					callRecordsModel.setFirstName(nextRecord[2]);
					callRecordsModel.setLastName(nextRecord[3]);
					callRecordsModel.setGroupName(nextRecord[4]);
					callRecordsModel.setCallingNumber(Long.valueOf(nextRecord[5]));
					callRecordsModel.setCalledNumber(Long.valueOf(nextRecord[6]));
					callRecordsModel.setCallStartDate((Date) null);
					callRecordsModel.setCallStartTime((Time) null);
					callRecordsModel.setCallENDDate((Date) null);
					callRecordsModel.setCallENDTime((Time) null);
					callRecordsModel.setCallId(String.format("%.0f", Double.parseDouble(nextRecord[11])));
					callRecordsModel.setBusinessGroup(nextRecord[12]);
					callRecordsModel.setPolicyNumber(nextRecord[13]);
					callRecordsModel.setApplicationNo(nextRecord[14]);
					callRecordsModel.setMetaFileName(filePath);
					callRecordsModel.setMetaFileUploadedBy(uploadedBy);
					callRecordsModel.setS3metaFilePath(filePath);
					callRecordsModel.setS3metabucketName(bucketName);
					callRecordsModelList.add(callRecordsModel);
					if (callRecordsModelList.size() == 10) {
						this.callRecordsRepo.saveAll(callRecordsModelList);
						callRecordsModelList.clear();
					}
				}
			}

			if (callRecordsModelList.size() > 0) {
				this.callRecordsRepo.saveAll(callRecordsModelList);
				callRecordsModelList.clear();
			}

			File f = new File(filePath);
			f.delete();
			return true;
		} catch (Exception var11) {
			var11.printStackTrace();
			return false;
		}
	}

	public List<String> getRecordFilePaths(List<MultipartFile> docfiles) throws IOException {
		List<String> filePaths = new ArrayList();
		Iterator var3 = docfiles.iterator();
		while (var3.hasNext()) {
			MultipartFile docfile = (MultipartFile) var3.next();
			String filePath = docfile.getOriginalFilename();
			File convFile = new File(filePath);
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(docfile.getBytes());
			fos.close();
			filePaths.add(filePath);
		}
		return filePaths;
	}

	public boolean putFilesOnS3(List<String> filePaths, String bucketName, String s3Location) {
		AmazonS3 s3Client = (AmazonS3) ((AmazonS3ClientBuilder) AmazonS3ClientBuilder.standard()
				.withRegion(Regions.AP_SOUTH_1)).build();

		try {
			Iterator var5 = filePaths.iterator();

			while (var5.hasNext()) {
				String filePath = (String) var5.next();
				File convFile = new File(filePath);
				String fileName = s3Location + "/" + filePath;
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, convFile);
				s3Client.putObject(putObjectRequest);
				URL s3Url = s3Client.getUrl(bucketName, filePath);
				System.out.println("Object URl:" + s3Url.toExternalForm());
			}

			return true;
		} catch (Exception var11) {
			var11.printStackTrace();
			return false;
		}
	}

	public boolean saveRecordingfileRecordsIntoDB(List<String> filePaths, String uploadedBy, String bucketName,
			List<String> s3PathRecordingKey) {
		List<CallRecordsModel> callRecordsModelList = new ArrayList();

		for (int i = 0; i < filePaths.size(); ++i) {
			CallRecordsModel callRecordsModel = this.callRecordsRepo.findbyID((String) filePaths.get(i));
			callRecordsModel.setS3RecordingBucketName(bucketName);
			callRecordsModel.setRecordingFileUploadedBy(uploadedBy);
			callRecordsModel.setS3RecordingFilePath((String) s3PathRecordingKey.get(i));
			callRecordsModelList.add(callRecordsModel);
			if (callRecordsModelList.size() == 10) {
				this.callRecordsRepo.saveAll(callRecordsModelList);
				callRecordsModelList.clear();
			}
		}

		if (callRecordsModelList.size() > 0) {
			this.callRecordsRepo.saveAll(callRecordsModelList);
			callRecordsModelList.clear();
		}

		return true;
	}

	public List<CallRecordsModel> findAll() {
		return this.callRecordsRepo.findAll();
	}

	public boolean processRecordFile(String category, String s3Location, String uploadedBy,
			List<MultipartFile> docfiles, String bucketName) {
		AmazonS3 s3Client = (AmazonS3) ((AmazonS3ClientBuilder) AmazonS3ClientBuilder.standard()
				.withRegion(Regions.AP_SOUTH_1)).build();
		try {
			Iterator var7 = docfiles.iterator();
			while (var7.hasNext()) {
				MultipartFile docfile = (MultipartFile) var7.next();
				String filePath = docfile.getOriginalFilename();
				File convFile = new File(filePath);
				FileOutputStream fos = new FileOutputStream(convFile);
				fos.write(docfile.getBytes());
				fos.close();
				String fileName = s3Location + "/" + filePath;
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, convFile);
				s3Client.putObject(putObjectRequest);
				s3Client.getUrl(bucketName, filePath);
			}
		} catch (Exception var15) {
		}

		return false;
	}

	public List<String> putRecordFilesOnS3(List<String> filePaths, String bucketName, String s3Location) {
		List<String> s3PathRecordingKey = new ArrayList();
		AmazonS3 s3Client = (AmazonS3) ((AmazonS3ClientBuilder) AmazonS3ClientBuilder.standard()
				.withRegion(Regions.AP_SOUTH_1)).build();
		try {
			Iterator var6 = filePaths.iterator();
			while (var6.hasNext()) {
				String filePath = (String) var6.next();
				File convFile = new File(filePath);
				String fileName = s3Location + "/" + filePath;
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, convFile);
				s3Client.putObject(putObjectRequest);
				URL s3Url = s3Client.getUrl(bucketName, fileName);
				System.out.println("Object URl:" + s3Url.toExternalForm());
				s3PathRecordingKey.add(s3Url.toExternalForm());
				convFile.delete();
			}
			return s3PathRecordingKey;
		} catch (Exception var12) {
			var12.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CallRecordsModel> fetchMetaFileByUser(String userId) throws Exception {
//		List<CallRecordsModel> metaFiles = callRecordsRepo.fetchMetaFileByUser(userId);
		return callRecordsRepo.fetchMetaFileByUser(userId);
	}
}
