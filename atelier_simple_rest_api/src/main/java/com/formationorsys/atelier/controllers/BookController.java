package com.formationorsys.atelier.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formationorsys.atelier.models.Book;
import com.formationorsys.atelier.service.BookService;

@RestController
@RequestMapping(path = "/api/v1/")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	// Récupérer l'ensemble des livres
	@GetMapping(path = "/books", produces = "application/json")
	public ResponseEntity<List<Book>> getBooks(){
		try {
			return new ResponseEntity<List<Book>>(bookService.getBooks(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Récupérer un livre au moyen de son ID
	@GetMapping(path = "/books/{id}", produces = "application/json")
	public ResponseEntity<Book> getBookByID(@PathVariable Long id){
		try {
			return new ResponseEntity<Book>(
					bookService.getBookByID(id),
					HttpStatus.OK
					);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// Ajouter un livre
	@PostMapping(path = "/books", produces = "application/json")
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		try {
			return new ResponseEntity<Book>(
					bookService.addBooks(book),
					HttpStatus.CREATED
					);		
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Mise à jour un livre
	@PutMapping(path = "/books/{id}", produces = "application/json")
	public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable Long id){
		try {
			return new ResponseEntity<Book>(bookService.updateBook(book, id), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/books/{id}")
	public ResponseEntity<HttpStatus> deleteBookByID(@PathVariable Long id){
		try {
			bookService.deleteBookByID(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(path = "/books")
	public ResponseEntity<HttpStatus> deleteBooks(){
		try {
			bookService.deleteBooks();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
