package com.example.library.library.controller;


import com.example.library.library.model.Book;
import com.example.library.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/")
@RestController
public class BookController {


    @Autowired
    BookRepository bookRepository;

    @GetMapping("books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("books")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);

    }

    //usuwanie danych
    @DeleteMapping("books/{isbn}")
    public ResponseEntity<Book> deleteBook(@PathVariable String isbn) {

        //wyszukuje książke do usunięcia
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);

        //jesli znaleziono książkę
        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("books/delete/id/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable long id) {

        //wyszukuje książke do usunięcia
        Optional<Book> optionalBook = bookRepository.findById(id);

        //jesli znaleziono książkę
        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("books/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book book) {

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            optionalBook.get().setAuthor(book.getAuthor());
            optionalBook.get().setTitle(book.getTitle());
            optionalBook.get().setIsbn(book.getIsbn());
            bookRepository.save(optionalBook.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
