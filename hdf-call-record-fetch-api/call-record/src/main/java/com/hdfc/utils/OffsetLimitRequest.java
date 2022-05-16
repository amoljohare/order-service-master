/* Decompiler 4ms, total 296ms, lines 55 */
package com.hdfc.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetLimitRequest implements Pageable {
   private int limit;
   private int offset;
   private String sortBy;
   private String sorttype;

   public OffsetLimitRequest(int limit, int offset, String sortBy, String sorttype) {
      this.limit = limit;
      this.offset = offset;
      this.sortBy = sortBy;
      this.sorttype = sorttype;
   }

   public int getPageNumber() {
      return 1;
   }

   public int getPageSize() {
      return this.limit;
   }

   public long getOffset() {
      return (long)this.offset;
   }

   public Pageable first() {
      return null;
   }

   public Sort getSort() {
      return this.sorttype.equals("asc") ? Sort.by(new String[]{this.sortBy}).ascending() : Sort.by(new String[]{this.sortBy}).descending();
   }

   public Pageable next() {
      return null;
   }

   public Pageable previousOrFirst() {
      return null;
   }

   public boolean hasPrevious() {
      return false;
   }

   public Pageable withPage(int pageNumber) {
      return null;
   }
}
