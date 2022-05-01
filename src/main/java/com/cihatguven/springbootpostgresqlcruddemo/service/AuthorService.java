package com.cihatguven.springbootpostgresqlcruddemo.service;


import com.cihatguven.springbootpostgresqlcruddemo.dto.AuthorDto;
import com.cihatguven.springbootpostgresqlcruddemo.dto.BookDto;
import com.cihatguven.springbootpostgresqlcruddemo.entity.Author;
import com.cihatguven.springbootpostgresqlcruddemo.exception.AuthorNotFoundException;
import com.cihatguven.springbootpostgresqlcruddemo.repository.AuthorRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookService bookService;

    public AuthorService(AuthorRepository authorRepository, @Lazy BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    public AuthorDto createAuthor(AuthorDto authorDto) {

        Author author = authorRepository.save(dozerBeanMapper.map(authorDto, Author.class));
        return dozerBeanMapper.map(author, AuthorDto.class);
    }

    public void deleteAuthor(Long id) {

        authorRepository.deleteById(id);
    }

    public List<AuthorDto> getAllAuthors() {
        List<Author> authorList = authorRepository.findAll();

        return authorList.stream().map(author -> dozerBeanMapper.map(author, AuthorDto.class)).collect(Collectors.toList());
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        List<BookDto> bookListDto = bookService.getAllBooksByAuthorId(id);

        AuthorDto authorDto = dozerBeanMapper.map(author, AuthorDto.class);
        authorDto.setBookList(bookListDto);
        return authorDto;
    }

    public AuthorDto updateAuthor(AuthorDto authorDto, Long id) {
        Author author = dozerBeanMapper.map(authorDto, Author.class);
        Author newAuthor = authorRepository.findById(id)
                .map(x -> {
                    x.setName(author.getName());
                    x.setLastName(author.getLastName());
                    return authorRepository.save(x);
                })
                .orElseGet(() -> {
                    author.setId(id);
                    return authorRepository.save(author);
                });
        return dozerBeanMapper.map(newAuthor, AuthorDto.class);
    }
}

