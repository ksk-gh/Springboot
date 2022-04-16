package com.javaspringbootlibrary;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaspringbootlibrary.controller.AddResponse;
import com.javaspringbootlibrary.controller.Library;
import com.javaspringbootlibrary.controller.LibraryController;
import com.javaspringbootlibrary.repository.LibraryRepository;
import com.javaspringbootlibrary.service.LibraryService;

@SpringBootTest
@AutoConfigureMockMvc

class SpringBootBookLibraryApplicationTests {

	@Autowired
	LibraryController con;
	@MockBean
	LibraryRepository repository;
	@MockBean
	LibraryService libraryService;
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogic() {
		LibraryService lib = new LibraryService();
		String id = lib.buildId("ZMAN", 24);
		assertEquals(id, "OLDZMAN24");
		String id1 = lib.buildId("MAN", 24);
		assertEquals(id1, "MAN24");
	}

	@Test
	public void addBookTest() {
		
		Library lib = buildLibrary();
		when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
		//when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(true);
		when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(lib);
		ResponseEntity response = con.addBookImplementation(buildLibrary());
		System.out.println(response.getStatusCodeValue());
		// assertEquals(response.getStatusCode(),HttpStatus.ACCEPTED);
		assertEquals(response.getStatusCode(),HttpStatus.CREATED);
		AddResponse ad= (AddResponse)response.getBody();
		ad.getId();
		assertEquals(lib.getId(),ad.getId());
		assertEquals("Success Book is Added..",ad.getMsg());
		
		
		//call Mock service from code
	}

	@Test
	public void addBookControllerTest() throws Exception
	{
		Library lib = buildLibrary();
		ObjectMapper map= new ObjectMapper();
		String jsonString = map.writeValueAsString(lib);
		
		when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
		//when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(true);
		when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(lib);
		this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andExpect(status().isAccepted());
		
	}
	
	
	
	public Library buildLibrary() {
		Library lib = new Library();
		lib.setAisle(322);
		lib.setBook_name("Spring");
		lib.setIsbn("sfe");
		lib.setAuthor("Rahul shetty");
		lib.setId("sfe3b");
		return lib;

	}

}
