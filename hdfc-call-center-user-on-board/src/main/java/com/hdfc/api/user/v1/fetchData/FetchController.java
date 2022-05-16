//package com.hdfc.api.user.v1.fetchData;
//
//import java.sql.Date;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hdfc.api.user.v1.UserModel;
//import com.hdfc.utils.StatusResponse;
//
//@CrossOrigin(origins = "*")
//@Transactional
//@RestController
//@RequestMapping(path = "v1/fetchReport")
//public class FetchController {
//	
//	@Autowired
//	FetchService fectService;
//	
//	FetchController(FetchService fectService){
//		super();
//		this.fectService=fectService;
//	}
//	@SuppressWarnings({ "unchecked", "rawtypes" })	
//	@GetMapping("/filter/{fromDate}/{toDate}/{ColumnName}")
//	public ResponseEntity<StatusResponse> listData(
//			@PathVariable("ColumnName") String ColumnName,
//			@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
//			@PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
//			@PathVariable("skip") int skip, @PathVariable("limit") int limit){
//		StatusResponse statusMessage = new StatusResponse();
//		try {
//			
//			Page<UserModel> DataList = null;
//			DataList = fectService.fetchAllRecord( ColumnName,fromDate, toDate, skip, limit);
//			
//			if(!DataList.isEmpty()) {
//				//statusMessage.setListData(DataList.getContent());
//				statusMessage.setMessage("Data fetch sucessfully");
//				statusMessage.setSuccess(true);
//				statusMessage.setStatusCode(200);
//				return new ResponseEntity<>(statusMessage, HttpStatus.CREATED);
//			}else {
//				statusMessage.setMessage("Data not found");
//				statusMessage.setSuccess(false);
//			}
//			
//		}catch (Exception e) {
//			statusMessage.setMessage(e.getMessage());
//			statusMessage.setSuccess(false);
//			return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
//		}
//		return null;
//		
//}
//}
