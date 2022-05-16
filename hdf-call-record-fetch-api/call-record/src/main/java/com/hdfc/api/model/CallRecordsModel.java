package com.hdfc.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "call_records_details")
public class CallRecordsModel {
	@Id
	@Column(name = "recording_file_name")
	private String recordingFileName;
	@Column(name = "meta_file_name")
	private String metaFileName;
	@Column(name = "meta_file_uploaded_by")
	private String metaFileUploadedBy;
	@Column(name = "s3_meta_file_path")
	private String s3metaFilePath;
	@Column(name = "s3_meta_bucket_name")
	private String s3metabucketName;
	@Column(name = "recording_file_uploaded_by")
	private String recordingFileUploadedBy;
	@Column(name = "s3_recording_file_path")
	private String s3RecordingFilePath;
	@Column(name = "s3_recording_bucket_name")
	private String s3RecordingBucketName;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "firs_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "group_name")
	private String groupName;
	@Column(name = "calling_number")
	private Long callingNumber;
	@Column(name = "called_number")
	private Long calledNumber;
	@Column(name = "call_start_date")
	private Date callStartDate;
	@Column(name = "call_start_time")
	private Time callStartTime;
	@Column(name = "call_end_date")
	private Date callENDDate;
	@Column(name = "call_end_time")
	private Time callENDTime;
	@Column(name = "call_id")
	private String callId;
	@Column(name = "business_group")
	private String businessGroup;
	@Column(name = "policy_number")
	private String policyNumber;
	@Column(name = "application_no")
	private String applicationNo;
	@Column(name = "category")
	private Integer category;
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at")
	private Date createdAt;

	public String getRecordingFileName() {
		return this.recordingFileName;
	}

	public void setRecordingFileName(String recordingFileName) {
		this.recordingFileName = recordingFileName;
	}

	public String getMetaFileName() {
		return this.metaFileName;
	}

	public void setMetaFileName(String metaFileName) {
		this.metaFileName = metaFileName;
	}

	public String getMetaFileUploadedBy() {
		return this.metaFileUploadedBy;
	}

	public void setMetaFileUploadedBy(String metaFileUploadedBy) {
		this.metaFileUploadedBy = metaFileUploadedBy;
	}

	public String getS3metaFilePath() {
		return this.s3metaFilePath;
	}

	public void setS3metaFilePath(String s3metaFilePath) {
		this.s3metaFilePath = s3metaFilePath;
	}

	public String getS3metabucketName() {
		return this.s3metabucketName;
	}

	public void setS3metabucketName(String s3metabucketName) {
		this.s3metabucketName = s3metabucketName;
	}

	public String getRecordingFileUploadedBy() {
		return this.recordingFileUploadedBy;
	}

	public void setRecordingFileUploadedBy(String recordingFileUploadedBy) {
		this.recordingFileUploadedBy = recordingFileUploadedBy;
	}

	public String getS3RecordingFilePath() {
		return this.s3RecordingFilePath;
	}

	public void setS3RecordingFilePath(String s3RecordingFilePath) {
		this.s3RecordingFilePath = s3RecordingFilePath;
	}

	public String getS3RecordingBucketName() {
		return this.s3RecordingBucketName;
	}

	public void setS3RecordingBucketName(String s3RecordingBucketName) {
		this.s3RecordingBucketName = s3RecordingBucketName;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getCallingNumber() {
		return this.callingNumber;
	}

	public void setCallingNumber(Long callingNumber) {
		this.callingNumber = callingNumber;
	}

	public Long getCalledNumber() {
		return this.calledNumber;
	}

	public void setCalledNumber(Long calledNumber) {
		this.calledNumber = calledNumber;
	}

	public Date getCallStartDate() {
		return this.callStartDate;
	}

	public void setCallStartDate(Date callStartDate) {
		this.callStartDate = callStartDate;
	}

	public Time getCallStartTime() {
		return this.callStartTime;
	}

	public void setCallStartTime(Time callStartTime) {
		this.callStartTime = callStartTime;
	}

	public Date getCallENDDate() {
		return this.callENDDate;
	}

	public void setCallENDDate(Date callENDDate) {
		this.callENDDate = callENDDate;
	}

	public Time getCallENDTime() {
		return this.callENDTime;
	}

	public void setCallENDTime(Time callENDTime) {
		this.callENDTime = callENDTime;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getBusinessGroup() {
		return this.businessGroup;
	}

	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}

	public String getPolicyNumber() {
		return this.policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getApplicationNo() {
		return this.applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public Integer getCategory() {
		return this.category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String toString() {
		return "CallRecordsModel [recordingFileName=" + this.recordingFileName + ", metaFileName=" + this.metaFileName
				+ ", metaFileUploadedBy=" + this.metaFileUploadedBy + ", s3metaFilePath=" + this.s3metaFilePath
				+ ", s3metabucketName=" + this.s3metabucketName + ", recordingFileUploadedBy="
				+ this.recordingFileUploadedBy + ", s3RecordingFilePath=" + this.s3RecordingFilePath
				+ ", s3RecordingBucketName=" + this.s3RecordingBucketName + ", userId=" + this.userId + ", firstName="
				+ this.firstName + ", lastName=" + this.lastName + ", groupName=" + this.groupName + ", callingNumber="
				+ this.callingNumber + ", calledNumber=" + this.calledNumber + ", callStartDate=" + this.callStartDate
				+ ", callStartTime=" + this.callStartTime + ", callENDDate=" + this.callENDDate + ", callENDTime="
				+ this.callENDTime + ", callId=" + this.callId + ", businessGroup=" + this.businessGroup
				+ ", policyNumber=" + this.policyNumber + ", applicationNo=" + this.applicationNo + ", category="
				+ this.category + ", createdAt=" + this.createdAt + "]";
	}
}
