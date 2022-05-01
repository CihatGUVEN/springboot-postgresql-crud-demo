package com.cihatguven.springbootpostgresqlcruddemo.request;

import lombok.Data;

@Data
public class BookUpdateRequest {
    private String name;
    private AuthorUpdateRequest author;
    private Double price;
}
