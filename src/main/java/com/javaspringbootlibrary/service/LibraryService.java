package com.javaspringbootlibrary.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javaspringbootlibrary.controller.Library;
import com.javaspringbootlibrary.repository.LibraryRepository;

@Service
public class LibraryService {

	@Autowired
	LibraryRepository repository;

	public String buildId(String isbn, int aisle) {

		if (isbn.startsWith("Z")) {
			return "OLD" + isbn + aisle;
		}

		return isbn + aisle;
	}

	public boolean checkBookAlreadyExist(String id) {
		Optional<Library> lib = repository.findById(id);
		if (lib.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

}