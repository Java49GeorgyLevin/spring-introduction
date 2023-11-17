package telran.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.exceptions.NotFoundException;
import telran.spring.controller.GreetingsController;
import telran.spring.service.GreetingsService;
record PersonIdString(String id, String name, String city, String mail, String phone) {
	
}
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
    Person personNormalUpdated = new Person(123, "Vasya", "Lod", "vasya@gmail.com",
    		"054-1234567");
    Person personWrongPhone = new Person(124, "Vasya", "Rehovot", "vasya@gmail.com",
    		"54-1234567");
    Person personWrongCity = new Person(124, "Vasya", null, "vasya@gmail.com",
    		"+972-54-1234567");
    Person personWrongName = new Person(123, "as", "Rehovot", "vasya@gmail.com",
    		"054-1234567");
    PersonIdString personWrongId = new PersonIdString("abc", "Vasya", "Rehovot", "vasya@gmail.com",
    		"054-1234567");
    Person personWrongMail = new Person(123, "Vasya", "Rehovot", "vasya",
    		"054-1234567");
    Person personNonExistingNormal = new Person(523, "Vanya", "Bat Yam", "vanya@gmail.com",
    		"054-1234567");
    Person personNormal2 = new Person(1234, "Wasya", "Rehovot", "wasya@gmail.com",
    		"054-1234567");
    Person personNormal3 = new Person(12345, "Yasya", "Rehovot", "yasya@gmail.com",
    		"054-1234567");
    
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
    	 when(greetingsService.addPerson(personNormal)).thenReturn(personNormal);
    	 String personJson = objectMapper.writeValueAsString(personNormal);
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(personJson))
    	 .andDo(print()).andExpect(status().isOk())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(personJson, response);
     }
     @Test
     void alreadyExistsAddPerson() throws Exception{
    	 String exceptionMessage = "already exists";
    	 when(greetingsService.addPerson(personNormal))
    	 .thenThrow(new IllegalStateException(exceptionMessage));
    	 String personJson = objectMapper.writeValueAsString(personNormal);
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(personJson))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(exceptionMessage, response);
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
     @Test
     void addPersonWrongMail() throws Exception{    	 
    	String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personWrongMail)))
    	 .andDo(print()).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
    	 assertEquals("must be a well-formed email address",response);
     }
     @Test
     void addPersonWrongCity() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personWrongCity)))
    	 .andDo(print()).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
    	 assertEquals("must not be empty",response);
     }
     @Test
     void addPersonWrongName() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personWrongName)))
    	 .andDo(print()).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
    	 assertEquals("Wrong name structure",response);
     }
     @Test
     void addPersonWrongId() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personWrongId)))
    	 .andDo(print()).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
	 assertEquals("JSON parse error: Cannot deserialize value of type `long` from String \"abc\": not a valid `long` value",response);
     }
     @Test
     void updatePersonTest() throws Exception {
    	 String personJSON = objectMapper.writeValueAsString(personNormalUpdated);
    	 when(greetingsService.updatePerson(personNormalUpdated)).thenReturn(personNormalUpdated);    	 
    	 String response = mockMvc.perform(put("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personNormalUpdated)))
    	 .andDo(print()).andExpect(status().isOk())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(personJSON, response);
     }
     @Test
     void updatePersonNonExistingTest() throws Exception {
    	 String personNotFoundMessage = String.format("Person with id %s was not found", personNonExistingNormal.id());
    	 when(greetingsService.updatePerson(personNonExistingNormal))
    	 	.thenThrow(new NotFoundException(personNotFoundMessage));
    	 String personJSON = objectMapper.writeValueAsString(personNonExistingNormal);
    	 String response = mockMvc.perform(put("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(personJSON))
    	 .andDo(print()).andExpect(status().isNotFound())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(personNotFoundMessage, response);    	 
     }     
       @Test
     void getPersonTest() throws Exception {
    	  String personJson = objectMapper.writeValueAsString(personNormal);
    	   when(greetingsService.getPerson(personNormal.id())).thenReturn(personNormal);
    	  String response= mockMvc.perform(get("http://localhost:8080/greetings/id/123"))
    	 .andDo(print()).andExpect(status().isOk())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(personJson, response);   	 
     }
     @Test
     void getExistingGreetingsTest() throws Exception {
    	 String stringHello = String.format("Hello, %s", personNormal.id());
    	 when(greetingsService.getGreetings(personNormal.id())).thenReturn(stringHello);
    	 String response = mockMvc.perform(get("http://localhost:8080/greetings/123"))
    	 .andDo(print()).andExpect(status().isOk())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(stringHello, response);
     }
     @Test
     void getAbsentingGreetingsTest() throws Exception {
    	 String stringNotFound = String.format("Person %s was not found", personNonExistingNormal.id());
    	 when(greetingsService.getGreetings(personNonExistingNormal.id())).thenReturn(stringNotFound);
    	 String response = mockMvc.perform(get("http://localhost:8080/greetings/523"))
    	 .andDo(print()).andExpect(status().isOk())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(stringNotFound, response);
     }
     @Test
     void getPersonsByCityTest() throws Exception {
    	 List<Person> list = new ArrayList<>(List.of(personNormal, personNormal2, personNormal3));
    	 String stringList = objectMapper.writeValueAsString(list);
    	 when(greetingsService.getPersonsByCity("Rehovot")).thenReturn(list);
    	 String response = mockMvc.perform(get("http://localhost:8080/greetings/city/Rehovot"))
    	 .andDo(print()).andExpect(status().isOk())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(stringList, response);
    	 
     }
     @Test
     void getPersonsByNoneCityTest() throws Exception {
    	 when(greetingsService.getPersonsByCity("Bnei Brak")).thenReturn(new ArrayList<>());
    	 Integer response = mockMvc.perform(get("http://localhost:8080/greetings/city/Bnei Brak"))
    	 .andDo(print()).andExpect(status().isOk())
    	 .andReturn().getResponse()
    	 .getContentLength();
    	 assertEquals(0, response);
     }
     @Test
     void deletePersonTest() throws Exception {
    	 String personJSON = objectMapper.writeValueAsString(personNormal);
    	 when(greetingsService.deletePerson(personNormal.id())).thenReturn(personNormal);
    	 String response = mockMvc.perform(delete("http://localhost:8080/greetings/123"))
    	 .andDo(print()).andExpect(status().isOk())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(personJSON, response);
     }
     @Test
     void deleteNonExistPersonTest() throws Exception {
    	 String stringNonExist = String.format("person with id %s not found", personNonExistingNormal.id());
    	 when(greetingsService.deletePerson(personNonExistingNormal.id()))
    	 .thenThrow(new NotFoundException(stringNonExist));
    	 String response = mockMvc.perform(delete("http://localhost:8080/greetings/523"))
    	 .andDo(print()).andExpect(status().isNotFound())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals(stringNonExist, response);
     }
     
}
