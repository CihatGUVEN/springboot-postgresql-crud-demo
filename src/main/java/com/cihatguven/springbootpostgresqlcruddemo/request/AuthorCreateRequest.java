package com.cihatguven.springbootpostgresqlcruddemo.request;

import lombok.Data;


@Data
public class AuthorCreateRequest {

    private int id;
    private String name;
    private String lastName;

}
