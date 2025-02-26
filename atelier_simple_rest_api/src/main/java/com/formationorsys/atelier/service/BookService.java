package com.formationorsys.atelier.service;

import java.util.List;

import com.formationorsys.atelier.models.Book;

public interface BookService {
	
	// Lister l'ensemble des livres
	List<Book> getBooks();
	
	// Retourner un livre au moyen de son ID
	Book getBookByID(Long id);
	
	// Ajouter un livre 
	Book addBooks(Book book);
	
	// Mise à jour d'un livre
	Book updateBook(Book book, Long id);
	
	// Supprimer un livre au moyen de son ID
	void deleteBookByID(Long id);
	
	// Supprimer tous les livres
	void deleteBooks();
	
}
