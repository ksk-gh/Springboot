package com.javaspringbootlibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaspringbootlibrary.controller.Library;

public interface LibraryRepository extends JpaRepository<Library, String>, LibraryRepositoryCustom {

}
