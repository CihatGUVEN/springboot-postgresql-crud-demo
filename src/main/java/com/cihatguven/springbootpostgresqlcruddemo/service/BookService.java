package com.cihatguven.springbootpostgresqlcruddemo.service;


import com.cihatguven.springbootpostgresqlcruddemo.dto.AuthorDto;
import com.cihatguven.springbootpostgresqlcruddemo.dto.BookDto;
import com.cihatguven.springbootpostgresqlcruddemo.entity.Book;
import com.cihatguven.springbootpostgresqlcruddemo.exception.BookNotFoundException;
import com.cihatguven.springbootpostgresqlcruddemo.exception.BookUnSupportedFieldPatchException;
import com.cihatguven.springbootpostgresqlcruddemo.repository.BookRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {


    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, @Lazy AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    public BookDto createBook(BookDto bookDto) {
        AuthorDto authorDto = authorService.createAuthor(bookDto.getAuthor());
        bookDto.setAuthor(authorDto);
        Book book = bookRepository.save(dozerBeanMapper.map(bookDto, Book.class));

        return dozerBeanMapper.map(book, BookDto.class);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();

        return bookList.stream().map(book -> dozerBeanMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }

    public BookDto updateBook(BookDto bookDto, Long id) {
        Book book = dozerBeanMapper.map(bookDto, Book.class);
        Book newBook = bookRepository.findById(id)
                .map(x -> {
                    x.setName(book.getName());
                    x.setAuthor(book.getAuthor());
                    x.setPrice(book.getPrice());
                    return bookRepository.save(x);
                })
                .orElseGet(() -> {
                    book.setId(id);
                    return bookRepository.save(book);
                });
        return dozerBeanMapper.map(newBook, BookDto.class);
    }

    public BookDto patchPrice(Map<String, Double> update, Long id) {

        Book book = bookRepository.findById(id)
                .map(x -> {
                    Double price = update.get("price");
                    if (!(price == null)) {
                        x.setPrice(price);
                        return bookRepository.save(x);
                    } else {
                        throw new BookUnSupportedFieldPatchException(update.keySet());
                    }
                }).orElseThrow(() -> new BookNotFoundException(id));

        return dozerBeanMapper.map(book, BookDto.class);
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return dozerBeanMapper.map(book, BookDto.class);
    }


    public List<BookDto> getAllBooksByAuthorId(Long id) {
        List<Book> bookList = bookRepository.getBooksByAuthor_Id(id);

        return bookList.stream()
                .map(book -> dozerBeanMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }

















}
