package com.cihatguven.springbootpostgresqlcruddemo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorResponse {
    private Long id;
    private String name;
    private String lastName;

}
