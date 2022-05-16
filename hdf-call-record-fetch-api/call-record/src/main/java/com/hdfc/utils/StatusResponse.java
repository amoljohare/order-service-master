package com.hdfc.utils;

import java.util.List;

public class StatusResponse<T> {
   int statusCode;
   String statusMessage;
   boolean success;
   Long totalCount;
   List<T> data;

   public int getStatusCode() {
      return this.statusCode;
   }

   public void setStatusCode(int statusCode) {
      this.statusCode = statusCode;
   }

   public String getStatusMessage() {
      return this.statusMessage;
   }

   public void setStatusMessage(String statusMessage) {
      this.statusMessage = statusMessage;
   }

   public boolean isSuccess() {
      return this.success;
   }

   public void setSuccess(boolean success) {
      this.success = success;
   }

   public Long getTotalCount() {
      return this.totalCount;
   }

   public void setTotalCount(Long totalCount) {
      this.totalCount = totalCount;
   }

   public List<T> getData() {
      return this.data;
   }

   public void setData(List<T> data) {
      this.data = data;
   }

   public String toString() {
      return "StatusResponse [statusCode=" + this.statusCode + ", statusMessage=" + this.statusMessage + ", success=" + this.success + ", totalCount=" + this.totalCount + ", data=" + this.data + "]";
   }
}
