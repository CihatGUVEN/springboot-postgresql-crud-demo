package com.cihatguven.springbootpostgresqlcruddemo;


import com.cihatguven.springbootpostgresqlcruddemo.dto.AuthorDto;
import com.cihatguven.springbootpostgresqlcruddemo.dto.BookDto;
import com.cihatguven.springbootpostgresqlcruddemo.entity.Author;
import com.cihatguven.springbootpostgresqlcruddemo.entity.Book;
import com.cihatguven.springbootpostgresqlcruddemo.repository.AuthorRepository;
import com.cihatguven.springbootpostgresqlcruddemo.repository.BookRepository;
import com.cihatguven.springbootpostgresqlcruddemo.service.AuthorService;
import com.cihatguven.springbootpostgresqlcruddemo.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    @Spy
    private BookService bookService;

    private Author author;
    private AuthorDto authorDto;
    private Book book;
    private BookDto bookDto;

    @Before
    public void init() {
        author = Author.builder()
                .id(1L)
                .name("ali")
                .lastName("veli")
                .build();

        authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .lastName(author.getLastName())
                .build();

        book = Book.builder()
                .id(1L)
                .name("ali ata bak")
                .author(author)
                .price(50.05)
                .build();

        bookDto = BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(authorDto)
                .price(book.getPrice())
                .build();

    }


    @Test
    public void createBook() {
        when(bookRepository.save(any())).thenReturn(book);
        when(authorService.createAuthor(bookDto.getAuthor())).thenReturn(authorDto);

        BookDto returnBookDto = bookService.createBook(bookDto);

        assertEquals(Optional.of(1L), Optional.ofNullable(returnBookDto.getId()));
        assertEquals("ali ata bak", returnBookDto.getName());

    }


    @Test
    public void deleteBook() {
        bookService.deleteBook(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    public void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<BookDto> bookDtoList = bookService.getAllBooks();
        assertEquals(1, bookDtoList.size());
        assertEquals("ali ata bak", bookDtoList.get(0).getName());
    }

    @Test
    public void updateBook() {
        when(bookRepository.findById(any())).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any())).thenReturn(book);
        BookDto bookReturnDto = bookService.updateBook(bookDto, 1L);
        assertEquals("ali ata bak", bookReturnDto.getName());

    }

    @Test
    public void getBookById() {
        when(bookRepository.findById(any())).thenReturn(Optional.ofNullable(book));
        BookDto bookReturnDto = bookService.getBookById(1L);
        assertEquals("ali ata bak", bookReturnDto.getName());
    }

    @Test
    public void patchPrice() {
        Map<String, Double> update = new HashMap<>();

        update.put("price", 60.0);
        when(bookRepository.findById(any())).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any())).thenReturn(book);
        BookDto patchedBookDto = bookService.patchPrice(update, 1L);


        assertEquals(Optional.of((60.0)), Optional.of(patchedBookDto.getPrice()));

    }

    @Test
    public void getAllBooksByAuthorId() {

        when(bookRepository.getBooksByAuthor_Id(any())).thenReturn(List.of(book));
        List<BookDto> bookDtoList = bookService.getAllBooksByAuthorId(1L);
        assertEquals(1L, bookDtoList.size());

    }


}
