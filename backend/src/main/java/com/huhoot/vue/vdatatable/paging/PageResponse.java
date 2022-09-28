package com.huhoot.vue.vdatatable.paging;

import com.huhoot.config.mvc.ICustomBodyResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResponse<T> implements ICustomBodyResponse {
    private List<T> list;
    private long totalElements;

    public <T> PageResponse(List<T> list, long totalElements) {

    }

    public PageResponse() {
        this.list = new ArrayList<>();
    }
}
