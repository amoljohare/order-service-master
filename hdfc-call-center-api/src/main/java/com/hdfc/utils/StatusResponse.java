package com.hdfc.utils;

public class StatusResponse<T> {

	int statusCode;
	String message;
	String name;
	String token;
	Integer userId;
	Integer roleId;
	boolean success;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "StatusResponse [statusCode=" + statusCode + ", message=" + message + ", name=" + name + ", token="
				+ token + ", userId=" + userId + ", roleId=" + roleId + ", success=" + success + "]";
	}

}
