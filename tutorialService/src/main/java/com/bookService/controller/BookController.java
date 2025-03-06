package com.bookService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.bookService.dao.BookRepository;
import com.bookService.model.Book;

@RestController
@RequestMapping(path = "/api")
public class BookController {
	
	@Autowired
	BookRepository tutorialRepository;
	
	//Retourner la liste des tutoriels
	@GetMapping(path = "/tutorials", produces = "application/json")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Book>> getTutorials(){
		List<Book> retrievedTutorials = (List<Book>) tutorialRepository.findAll();
		try {
			if(! retrievedTutorials.isEmpty()) {
				return new ResponseEntity<List<Book>>(retrievedTutorials,HttpStatus.OK);
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//Retourner un tutoriel au moyen de son ID
	@GetMapping(path = "/tutorials/{id}", produces = "application/json")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Book> getTutorialByID(@PathVariable String id){
		try {
			Optional<Book>  retrievedTutorial =  tutorialRepository.findById(id);
			
			if(retrievedTutorial.isPresent()) {
				System.out.println("ID tutoriel : "+retrievedTutorial.get().getId().toString());
	
				return new ResponseEntity<Book>(retrievedTutorial.get(),HttpStatus.OK);
			}
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//Ajouter un tutoriel
	@PostMapping(path = "/tutorials")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> addTutorial(@RequestBody Book tutorial){
		try {
			
			System.out.println("------ Début du traitement ------");
			System.out.println(tutorial.getId());
			System.out.println(tutorial.getTitle());
			System.out.println(tutorial.getDescription());
			
			Book _tutorial = tutorialRepository.save(new Book(tutorial.getTitle(),
					tutorial.getDescription(), tutorial.isPublish())) ;
			
			return new ResponseEntity<Book>(_tutorial,HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("----- Une erreur est survenue -----");
			return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Ajouter une liste de tutoriels 
	@PostMapping(path = "/tutorials/liste", produces = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Book>> addTutorialsList(@RequestBody List<Book>  tutorials){
		try {
			for (Book tutorial : tutorials) {
				tutorialRepository.save(tutorial);
			}
			return new ResponseEntity<List<Book>>(tutorials,HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	//Supprimer un tutoriel
	@DeleteMapping(path ="/tutorials/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteTutorialByID(@PathVariable String id){
		tutorialRepository.deleteById(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	//Supprimer tous les tutoriels
	@DeleteMapping(path = "/tutorials")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteTutorials(){
		tutorialRepository.deleteAll();
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
