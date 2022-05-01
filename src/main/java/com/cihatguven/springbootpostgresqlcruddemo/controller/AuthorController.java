package com.cihatguven.springbootpostgresqlcruddemo.controller;


import com.cihatguven.springbootpostgresqlcruddemo.dto.AuthorDto;
import com.cihatguven.springbootpostgresqlcruddemo.request.AuthorCreateRequest;
import com.cihatguven.springbootpostgresqlcruddemo.request.AuthorUpdateRequest;
import com.cihatguven.springbootpostgresqlcruddemo.response.AuthorGetByIdResponse;
import com.cihatguven.springbootpostgresqlcruddemo.response.AuthorResponse;
import com.cihatguven.springbootpostgresqlcruddemo.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorCreateRequest authorCreateRequest) {
        AuthorDto authorDto = authorService.createAuthor(dozerBeanMapper.map(authorCreateRequest, AuthorDto.class));

        return ResponseEntity.ok(dozerBeanMapper.map(authorDto, AuthorResponse.class));
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {

        List<AuthorDto> authorDtoList = authorService.getAllAuthors();
        List<AuthorResponse> authorResponseList = authorDtoList.stream()
                .map(authorDto -> dozerBeanMapper.map(authorDto, AuthorResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(authorResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorGetByIdResponse> getAuthorById(@PathVariable Long id) {
        AuthorDto authorDto = authorService.getAuthorById(id);
        return ResponseEntity.ok(dozerBeanMapper.map(authorDto, AuthorGetByIdResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@RequestBody AuthorUpdateRequest authorUpdateRequest, @PathVariable Long id) {
        AuthorDto authorDto = dozerBeanMapper.map(authorUpdateRequest, AuthorDto.class);
        AuthorDto updatedAuthorDto = authorService.updateAuthor(authorDto, id);

        return ResponseEntity.ok(dozerBeanMapper.map(updatedAuthorDto, AuthorResponse.class));
    }

}
