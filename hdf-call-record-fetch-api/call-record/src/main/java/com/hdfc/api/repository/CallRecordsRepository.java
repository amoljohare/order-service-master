package com.hdfc.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hdfc.api.model.CallRecordsModel;

@Repository
public interface CallRecordsRepository extends JpaRepository<CallRecordsModel, Integer> {
	@Query("SELECT r FROM CallRecordsModel r WHERE r.recordingFileName = :id")
	CallRecordsModel findbyID(String id);

	@Query("SELECT m FROM CallRecordsModel m WHERE m.userId = :userId")
	List<CallRecordsModel> fetchMetaFileByUser(String userId);
}
