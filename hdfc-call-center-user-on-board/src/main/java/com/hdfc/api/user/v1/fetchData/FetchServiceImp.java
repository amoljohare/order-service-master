//package com.hdfc.api.user.v1.fetchData;
//
//import java.sql.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import com.hdfc.api.user.v1.UserModel;
//import com.hdfc.api.user.v1.UserRepository;
//import com.hdfc.utils.OffsetLimitRequest;
//
//@Service
//public class FetchServiceImp implements FetchService {
//
//	@Autowired
//	UserRepository userRepository;
//	
//	@Override
//	public Page<UserModel> fetchAllRecord(String ColumnName,Date fromDate, Date toDate, int skip, int limit) {
//		
//		Pageable pageable;
//		if (skip != 0) {
//			pageable = new OffsetLimitRequest(skip, limit, "messageId");
//
//		} else {
//			pageable = new OffsetLimitRequest(0, 10, "messageId");
//		}
//		
//		Page<UserModel> result = null;
//		
//		//String Var =ColumnName;
//		
//		if(ColumnName.equals("all")) {
//			
//		//	result = userRepository.findAllWithUserId(pageable,ColumnName,fromDate,toDate,skip,limit);
//			
//		}else {
//		//	result = userRepository.findAllWithColumnName(pageable,ColumnName,fromDate,toDate,skip,limit);
//		}
//		
//		return result;
//	}
//
//}
