package com.javaspringbootlibrary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javaspringbootlibrary.controller.Library;
import com.javaspringbootlibrary.repository.LibraryRepository;

@SpringBootApplication
public class SpringBootBookLibraryApplication{

	@Autowired
	LibraryRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBookLibraryApplication.class, args);
	}

	/*
	 * @Override public void run(String[] args) { Library lib =
	 * repository.findById("fdsefr344").get(); System.out.println(lib.getAuthor());
	 * Library en =new Library(); en.setAisle(123); en.setAuthor("Sankar");
	 * en.setBook_name("Spring insert"); en.setIsbn("DEV123");
	 * en.setId("DEV123456"); repository.save(en); List<Library> allrecords
	 * =repository.findAll(); for(Library item : allrecords) {
	 * System.out.println(item.getBook_name()); }
	 * 
	 * repository.delete(en); }
	 */
}
