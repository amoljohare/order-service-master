package com.hdfc.utils;

import org.springframework.data.domain.Page;

public class StatusMessage<T> {

	private boolean success;
	private String message;
	private Long totalCount;
// List<T> ListData;
	Page<T> PageData;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Page<T> getPageData() {
		return PageData;
	}

	public void setPageData(Page<T> pageData) {
		PageData = pageData;
	}

// public List<T> getListData() {
// return ListData;
// }
//
// public void setListData(List<T> listData) {
// ListData = listData;
// }

}