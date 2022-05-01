package com.cihatguven.springbootpostgresqlcruddemo.repository;


import com.cihatguven.springbootpostgresqlcruddemo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

//  bu metot yazar id sine göre o yazarın tüm kitaplarını repodan getirir.
    List<Book> getBooksByAuthor_Id(Long id);

}
