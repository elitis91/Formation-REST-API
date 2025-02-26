package com.formationorsys.atelier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.formationorsys.atelier.models.Book;

@Repository
public interface BookDAO extends JpaRepository<Book, Long> {

}
