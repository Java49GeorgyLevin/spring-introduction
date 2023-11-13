package telran.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.spring.controller.GreetingsController;
import telran.spring.service.GreetingsService;

@WebMvcTest
public class GreetingsControllerTest {
    @Autowired //this annotation allows dependency injection inside following field 
	GreetingsController controller;
    @MockBean
    GreetingsService greetingsService;
    @Autowired
    MockMvc mockMvc; //imitator of Web Server
    Person personNormal = new Person(123, "Vasya", "Rehovot", "vasya@gmail.com",
    		"054-1234567");
    Person personWrongPhone = new Person(124, "Vasya", "Rehovot", "vasya@gmail.com",
    		"54-1234567");
    @Autowired
    ObjectMapper objectMapper;
     @Test
     void applicationContextTest() {
    	 assertNotNull(controller);
    	 assertNotNull(greetingsService);
    	 assertNotNull(mockMvc);
    	 assertNotNull(objectMapper);
    	 
     }
     @Test
     void normalFlowAddPerson() throws Exception{
    	 mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personNormal)))
    	 .andDo(print()).andExpect(status().isOk());
     }
     @Test
     void addPersonWrongPhone() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personWrongPhone)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals("not Israel mobile phone",response);
     }
}
