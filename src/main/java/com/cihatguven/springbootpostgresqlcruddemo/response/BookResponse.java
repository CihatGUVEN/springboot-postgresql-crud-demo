package com.cihatguven.springbootpostgresqlcruddemo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookResponse {

    private Long id;

    private String name;

    private AuthorResponse author;

    private Double price;
}
