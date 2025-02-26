package com.formationorsys.atelier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formationorsys.atelier.dao.BookDAO;
import com.formationorsys.atelier.models.Book;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookDAO bookDAO;
	
	// Retourne tous les livres
	@Override
	public List<Book> getBooks() {
		List<Book> books = new ArrayList<Book>();
		bookDAO.findAll().forEach(books::add);
		return books;
	}
	
	// Retourne un livre spécifique au moyen de son ID
	@Override
	public Book getBookByID(Long id) {
		// TODO Auto-generated method stub
		Optional<Book> _retrievedBook = bookDAO.findById(id);
		if (_retrievedBook.isPresent()) {
			return _retrievedBook.get();
		}
		return null;
 	}
	// Mettre à jour un Book
	@Override
	public Book updateBook(Book book, Long id) {
		// TODO Auto-generated method stub
		Optional<Book> _retrievedBook = bookDAO.findById(id);
		
		if(_retrievedBook.isPresent()) {
			Book _book = _retrievedBook.get();
			_book.setTitle(book.getTitle());
			_book.setDescription(book.getDescription());
			_book.setPublished(book.isPublished());
			
			return _book;
		}
		return null;
	}
	
	
	// Sauvegarde d'un nouveau livre
	@Override
	public Book addBooks(Book book) {
		Book _book = bookDAO.save(new Book( 
				book.getTitle(),
				book.getDescription(),
				book.isPublished())
				);
		return _book;
	}
	
	// Supprimer un livre au moyen de son ID
	@Override
	public void deleteBookByID(Long id) {
		bookDAO.deleteById(id);
	}

	
	// Supprimer tous les livres 
	@Override
	public void deleteBooks() {
		bookDAO.deleteAll();
	}



}
