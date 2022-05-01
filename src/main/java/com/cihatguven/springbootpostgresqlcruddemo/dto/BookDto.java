package com.cihatguven.springbootpostgresqlcruddemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    private String name;

    private AuthorDto author;

    private Double price;

}
