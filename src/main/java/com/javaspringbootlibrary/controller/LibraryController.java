package com.javaspringbootlibrary.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.javaspringbootlibrary.repository.LibraryRepository;
import com.javaspringbootlibrary.service.LibraryService;

@RestController
public class LibraryController {

	@Autowired
	LibraryRepository repository;

	@Autowired
	LibraryService libraryService;

	// private static final Logger logger =
	// LoggerFactory.getLogger(LibraryController.class);

	private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

	@PostMapping("/addBook")
	// public AddResponse addBookImplementation(@RequestBody Library library)
	public ResponseEntity<AddResponse> addBookImplementation(@RequestBody Library library) {
		String id = libraryService.buildId(library.getIsbn(), library.getAisle());
		libraryService.checkBookAlreadyExist(id);
		AddResponse ad = new AddResponse();

		if (!libraryService.checkBookAlreadyExist(id)) {
			logger.info("Book does not exists so creating one");
			library.setId(id);
			// library.getAuthor();
			repository.save(library);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Unique", id);
			ad.setMsg("Success Book is Added..");
			ad.setId(id);
			// return ad;
			return new ResponseEntity<AddResponse>(ad, headers, HttpStatus.CREATED);
		} else {
			logger.info("Book already exists so skipping creating new Book");

			ad.setMsg("Book Already exist");
			ad.setId(id);
			return new ResponseEntity<AddResponse>(ad, HttpStatus.ACCEPTED);

		}

	}

	@GetMapping("/getBooks/{id}")
	public Library getBooksById(@PathVariable(value = "id") String id) {
		try {
			Library lib = repository.findById(id).get();
			return lib;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/getBooks/author")
	public List<Library> getBooksByAuthorName(@RequestParam(value = "authorName") String authorName) {
		return repository.findAllByAuthor(authorName);

	}

	@PutMapping("/updateBook/{id}")
	public ResponseEntity<Library> updateBook(@PathVariable(value = "id") String id, @RequestBody Library library) {
		Library existingBook = repository.findById(id).get();
		existingBook.setAisle(library.getAisle());
		existingBook.setAuthor(library.getAuthor());
		existingBook.setBook_name(library.getBook_name());
		repository.save(existingBook);
		logger.info("Book detail got updated");
		return new ResponseEntity<Library>(existingBook, HttpStatus.OK);

	}

	@DeleteMapping("/deleteBook")
	public ResponseEntity<String> deleteBookById(@RequestBody Library library) {

		// String id=libraryService.buildId(library.getId());
		// libraryService.checkBookAlreadyExist(id);
		try {
			Library delLibrary = repository.findById(library.getId()).get();
			repository.delete(delLibrary);
			logger.info("Book got deleted");
			return new ResponseEntity<>("Data is Deleted", HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			return new ResponseEntity<>("Given Book Id is Not found", HttpStatus.NOT_FOUND);
		}

	}

}
