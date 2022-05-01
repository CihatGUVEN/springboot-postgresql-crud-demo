package com.cihatguven.springbootpostgresqlcruddemo;


import com.cihatguven.springbootpostgresqlcruddemo.dto.AuthorDto;
import com.cihatguven.springbootpostgresqlcruddemo.dto.BookDto;
import com.cihatguven.springbootpostgresqlcruddemo.entity.Author;
import com.cihatguven.springbootpostgresqlcruddemo.entity.Book;
import com.cihatguven.springbootpostgresqlcruddemo.repository.AuthorRepository;
import com.cihatguven.springbootpostgresqlcruddemo.service.AuthorService;
import com.cihatguven.springbootpostgresqlcruddemo.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.ArgumentMatchers;
import org.junit.Assert;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookService bookService;

    @InjectMocks
    @Spy
    private AuthorService authorService;

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
    public void createAuthor() {
        when(authorRepository.save(any())).thenReturn(author);

        AuthorDto returnAuthorDto = authorService.createAuthor(authorDto);

        assertEquals(Optional.of(1L), Optional.ofNullable(returnAuthorDto.getId()));
        assertEquals("ali", returnAuthorDto.getName());

    }

    @Test
    public void deleteAuthor() {
        authorService.deleteAuthor(1L);
        verify(authorRepository).deleteById(1L);
    }

    @Test
    public void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author));
        List<AuthorDto> authorDtoList = authorService.getAllAuthors();
        assertEquals(1, authorDtoList.size());
        assertEquals("ali", authorDtoList.get(0).getName());
    }

    @Test
    public void getAuthorById(){
     when(authorRepository.findById(any())).thenReturn(Optional.ofNullable(author));
     when(bookService.getAllBooksByAuthorId(any())).thenReturn(List.of(bookDto));
     AuthorDto authorReturnDto = authorService.getAuthorById(1L);
     assertEquals("ali",authorReturnDto.getName());
    }

    @Test
    public void updateAuthor(){
       when(authorRepository.findById(any())).thenReturn(Optional.ofNullable(author));
       when(authorRepository.save(any())).thenReturn(author);
        AuthorDto authorReturnDto = authorService.updateAuthor(authorDto, 1L);
        assertEquals("ali",authorReturnDto.getName());

    }
}
