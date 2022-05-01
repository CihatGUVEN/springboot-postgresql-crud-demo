package com.cihatguven.springbootpostgresqlcruddemo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AuthorGetByIdResponse {

    private Long id;

    private String name;

    private String lastName;

    private List<BookAuthorResponse> bookList;

}
