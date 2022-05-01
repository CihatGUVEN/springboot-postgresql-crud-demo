package com.cihatguven.springbootpostgresqlcruddemo.request;

import lombok.Data;


@Data
public class BookCreateRequest {

    private String name;
    private AuthorCreateRequest author;
    private Double price;

}
