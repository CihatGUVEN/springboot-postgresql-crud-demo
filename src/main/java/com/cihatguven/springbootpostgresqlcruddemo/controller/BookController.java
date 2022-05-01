package com.cihatguven.springbootpostgresqlcruddemo.controller;


import com.cihatguven.springbootpostgresqlcruddemo.dto.BookDto;
import com.cihatguven.springbootpostgresqlcruddemo.request.BookCreateRequest;
import com.cihatguven.springbootpostgresqlcruddemo.request.BookUpdateRequest;
import com.cihatguven.springbootpostgresqlcruddemo.response.BookResponse;
import com.cihatguven.springbootpostgresqlcruddemo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookCreateRequest bookCreateRequest) {
        BookDto bookDto = bookService.createBook(dozerBeanMapper.map(bookCreateRequest, BookDto.class));

        return ResponseEntity.ok(dozerBeanMapper.map(bookDto, BookResponse.class));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {

        List<BookDto> bookDtoList = bookService.getAllBooks();
        List<BookResponse> bookResponseList = bookDtoList.stream()
                .map(bookDto -> dozerBeanMapper.map(bookDto, BookResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        BookDto bookDto = bookService.getBookById(id);
        return ResponseEntity.ok(dozerBeanMapper.map(bookDto, BookResponse.class));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@RequestBody BookUpdateRequest bookUpdateRequest, @PathVariable Long id) {
        BookDto bookDto = dozerBeanMapper.map(bookUpdateRequest, BookDto.class);
        BookDto updatedBookDto = bookService.updateBook(bookDto, id);

        return ResponseEntity.ok(dozerBeanMapper.map(updatedBookDto, BookResponse.class));
    }
//todo request response
    @PatchMapping("/{id}")
    public BookDto patchAuthor(@RequestBody Map<String, Double> update, @PathVariable Long id) {
        return bookService.patchPrice(update, id);
    }

}
    

