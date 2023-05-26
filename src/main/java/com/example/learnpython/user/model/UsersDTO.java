package com.example.learnpython.user.model;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class UsersDTO {
    private List<UserResponse> results;
    private long totalElements;
    private int totalPages;
    private int intCurrentPage;
    private boolean isFirst;
    private boolean isLast;

    public UsersDTO(final Page<UserResponse> resultsPage){
        this.results = resultsPage.getContent();
        this.totalElements = resultsPage.getTotalElements();
        this.totalPages = resultsPage.getTotalPages();
        this.intCurrentPage = resultsPage.getNumber() + 1;
        this.isFirst = resultsPage.isFirst();
        this.isLast = resultsPage.isLast();
    }
}
