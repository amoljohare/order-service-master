package com.hdfc.api.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hdfc.api.model.CallRecordsModel;
import com.hdfc.api.service.CallRecordsService;
import com.hdfc.utils.JWTokenVerify;
import com.hdfc.utils.StatusResponse;

@CrossOrigin(origins = { "*" })
@RestController
public class CallRecordsController {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${maskSecretKey}")
	private String maskSecretKey;

	@Autowired
	private CallRecordsService callCenterService;

	@GetMapping(value = "/meta-file/{userId}")
	public ResponseEntity<StatusResponse> fetchMetaFileByUser(@PathVariable("userId") String userId) {
		StatusResponse statusMessage = new StatusResponse();
		try {
			List<CallRecordsModel> metaFiles = callCenterService.fetchMetaFileByUser(userId);
			statusMessage.setStatusMessage("Meta Files Successfully Fetched");
			statusMessage.setSuccess(true);
			statusMessage.setStatusCode(200);
			statusMessage.setData(metaFiles);
			return new ResponseEntity(statusMessage, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping({ "/meta-file/{category}/{s3Location}/{uploadedBy}" })
	public ResponseEntity<StatusResponse> saveMetaFile(@PathVariable("category") String category,
			@PathVariable("s3Location") String s3Location, @PathVariable("uploadedBy") String uploadedBy,
			@RequestParam(value = "docfile", required = false) MultipartFile docfile,
			@RequestHeader("x-access-token") String token) {
		StatusResponse statusMessage = new StatusResponse();

		try {
			JSONObject verify = JWTokenVerify.verifyJWT(token, this.secret);
			String bucketName = "testbucketstorages";
			if (verify.getString("message").equals("valid")) {
				String filePath = this.callCenterService.getFilePath(docfile);
				boolean uploadFlag = this.callCenterService.putFileOnS3(filePath, bucketName, s3Location);
				if (uploadFlag) {
					boolean recordUpdate = this.callCenterService.saveMetafileRecordsIntoDB(filePath, uploadedBy,
							bucketName);
					if (recordUpdate) {
						statusMessage.setStatusMessage("Successfully Done");
						statusMessage.setSuccess(true);
						statusMessage.setStatusCode(200);
						return new ResponseEntity(statusMessage, HttpStatus.OK);
					} else {
						statusMessage.setStatusMessage("Internal Server Error");
						statusMessage.setSuccess(true);
						statusMessage.setStatusCode(500);
						return new ResponseEntity(statusMessage, HttpStatus.OK);
					}
				} else {
					statusMessage.setStatusMessage("Internal Server Error");
					statusMessage.setSuccess(true);
					statusMessage.setStatusCode(500);
					return new ResponseEntity(statusMessage, HttpStatus.OK);
				}
			} else {
				statusMessage.setStatusMessage("Invalid Token");
				statusMessage.setSuccess(false);
				return new ResponseEntity(statusMessage, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception var12) {
			var12.printStackTrace();
			statusMessage.setStatusMessage(var12.getMessage());
			statusMessage.setSuccess(false);
			return new ResponseEntity(statusMessage, HttpStatus.BAD_REQUEST);
		}
	}

//	@SuppressWarnings("rawtypes")
//	@PostMapping("/meta-file/{metaFileName}/{userId}")
//	public ResponseEntity<StatusResponse> saveRecodingToS3(@PathVariable("metaFileName") String metaFileName,
//			@PathVariable("userId") String userId,
//			@RequestParam(value = "file", required = false) List<MultipartFile> file) {
//		StatusResponse statusMessage = new StatusResponse();
//		try {
////			List<String> filePaths = this.callCenterService.getRecordFilePaths(docfile);
////			List<String> s3PathRecordingKey = this.callCenterService.putRecordFilesOnS3(filePaths, bucketName,
////					s3Location);
//			boolean recordUpdate = this.callCenterService.saveRecordingfileRecordsIntoDB(filePaths, uploadedBy,
//					bucketName, s3PathRecordingKey);
//			if (recordUpdate) {
//				statusMessage.setStatusMessage("Successfully Done");
//				statusMessage.setSuccess(true);
//				statusMessage.setStatusCode(200);
//				return new ResponseEntity(statusMessage, HttpStatus.OK);
//			} else {
//				statusMessage.setStatusMessage("Internal Server Error");
//				statusMessage.setSuccess(true);
//				statusMessage.setStatusCode(500);
//				return new ResponseEntity(statusMessage, HttpStatus.OK);
//			}
//		} catch (Exception var12) {
//			var12.printStackTrace();
//			statusMessage.setStatusMessage(var12.getMessage());
//			statusMessage.setSuccess(false);
//			return new ResponseEntity(statusMessage, HttpStatus.BAD_REQUEST);
//		}
//	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/meta-file/{metaFileName}/{s3Location}/{userId}")
	public ResponseEntity<StatusResponse> saveRecodingToS3(@PathVariable("metaFileName") String metaFileName,
			@PathVariable("s3Location") String s3Location, @PathVariable("userId") String userId,
			@RequestParam(value = "file", required = false) List<MultipartFile> file) {
		StatusResponse statusMessage = new StatusResponse();
		try {
			String bucketName = "testbucketstorages";
			// fetch all file path from by iterating actual files
			List<String> fileNames = this.callCenterService.getRecordFilePaths(file);
			// put file in s3 bucket - got s3 url
			List<String> s3PathRecordingKey = this.callCenterService.putRecordFilesOnS3(fileNames, bucketName,
					s3Location);
			// update s3 link in db
			boolean s3UrlUpdate = this.callCenterService.saveRecordingfileRecordsIntoDB(fileNames, userId, bucketName,
					s3PathRecordingKey);
			if (s3UrlUpdate) {
				statusMessage.setStatusMessage("S3 URL Updated Successfully!");
				statusMessage.setSuccess(true);
				statusMessage.setStatusCode(200);
				return new ResponseEntity(statusMessage, HttpStatus.OK);
			} else {
				statusMessage.setStatusMessage("Internal Server Error");
				statusMessage.setSuccess(true);
				statusMessage.setStatusCode(500);
				return new ResponseEntity(statusMessage, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			statusMessage.setStatusMessage(e.getMessage());
			statusMessage.setSuccess(false);
			return new ResponseEntity(statusMessage, HttpStatus.BAD_REQUEST);
		}
	}
}
