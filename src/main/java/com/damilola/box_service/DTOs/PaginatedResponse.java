package com.damilola.box_service.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {

    private int totalContents;
    private String totalPages; // e.g "1/10"
    private int totalItems;
    private List<T> contents;
}
