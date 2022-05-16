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
    @Override
    public int getPageNumber() {
        return 1;
    }
    @Override
    public int getPageSize() {
        return this.limit;
    }
    @Override
    public long getOffset() {
        return this.offset;
    }
    @Override
    public Pageable first() {
        return null;
    }
    @Override
    public Sort getSort() {
        if (sorttype.equals("asc")) {
            return Sort.by(this.sortBy).ascending();
        } else {
            return Sort.by(this.sortBy).descending();
        }
    }
    @Override
    public Pageable next() {
        return null;
    }
    @Override
    public Pageable previousOrFirst() {
        return null;
    }
    @Override
    public boolean hasPrevious() {
        return false;
    }
    @Override
    public Pageable withPage(int pageNumber) {
        // TODO Auto-generated method stub
        return null;
    }
}