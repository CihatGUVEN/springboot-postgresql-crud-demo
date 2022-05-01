package com.cihatguven.springbootpostgresqlcruddemo.request;

import lombok.Data;

@Data
public class AuthorUpdateRequest {
    private String name;
    private String lastName;
}
