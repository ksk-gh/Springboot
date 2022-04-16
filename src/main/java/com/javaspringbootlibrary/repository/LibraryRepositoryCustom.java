package com.javaspringbootlibrary.repository;
import java.util.List;

import com.javaspringbootlibrary.controller.Library;
public interface LibraryRepositoryCustom {
	
	List<Library> findAllByAuthor(String authorName);

}
