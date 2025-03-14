package com.bookService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookService.model.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
