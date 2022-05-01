package com.cihatguven.springbootpostgresqlcruddemo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookAuthorResponse {

    private Long id;

    private String name;

    private Double price;
}
