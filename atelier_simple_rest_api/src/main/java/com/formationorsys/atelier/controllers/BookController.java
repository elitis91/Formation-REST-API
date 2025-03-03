package com.formationorsys.atelier.controllers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
	
	// Récupération de l'ensemble des livres
	@GetMapping(path = "/books", produces = "application/json")
	public ResponseEntity<List<EntityModel<Book>>> getBooks(){
		List<Book> books = bookService.getBooks();
		
		List<EntityModel<Book>> bookRessources = books.stream().map(book -> {
			EntityModel<Book> resource = EntityModel.of(book);
			resource.add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(BookController.class).getBookByID(book.getId())
					).withRel("all-books"));
			
			return resource;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(bookRessources);	
	}	
	
	// Récupérer un livre au moyen de son ID
	@GetMapping(path = "/books/{id}", produces = "application/json")
	public ResponseEntity<EntityModel<Book>> getBookByID(@PathVariable Long id){
		
		Book book = bookService.getBookByID(id);
        EntityModel<Book> resource = EntityModel.of(book);
        resource.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(BookController.class).getBookByID(id)
        ).withSelfRel());
        
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).getBooks()
            ).withRel("all-books"));

        return ResponseEntity.ok(resource);	
	}
	
	
	// Ajouter un livre
	@PostMapping(path = "/books", produces = "application/json")
	public ResponseEntity<EntityModel<Book>> addBook(@RequestBody Book book){
		
        Book savedBook = bookService.addBooks(book);

        EntityModel<Book> resource = EntityModel.of(savedBook);
        resource.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(BookController.class).getBookByID(savedBook.getId())
        ).withSelfRel());

        return ResponseEntity.created(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(BookController.class).getBookByID(savedBook.getId())
            ).toUri()
        ).body(resource); 

	}
	
	// Mise à jour un livre
	@PutMapping(path = "/books/{id}", produces = "application/json")
	public ResponseEntity<EntityModel<Book>> updateBook(@RequestBody Book book, @PathVariable Long id){
		
        Book updatedBook = bookService.updateBook(book, id);

	    EntityModel<Book> resource = EntityModel.of(updatedBook);
	    resource.add(WebMvcLinkBuilder.linkTo(
	        WebMvcLinkBuilder.methodOn(BookController.class).getBookByID(id)
	    ).withSelfRel());
	
	    return ResponseEntity.ok(resource);
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
