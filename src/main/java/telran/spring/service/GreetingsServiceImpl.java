package telran.spring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import telran.spring.Person;
@Service
public class GreetingsServiceImpl implements GreetingsService {
	Person p123 = new Person(123l, "David", "Tel Aviv");
	Person p124 = new Person(124l, "Sara", "Bnei Brak");
	Person p125 = new Person(125l, "Rivka", "Petah Tikva");
	Map<Long, Person> personsMap = new HashMap<>(Map.of(123l, p123, 124l, p124, 125l, p125));

	
	Map<String, List<Person>> citiesMap = new HashMap<>();	
	private HashMap<String, List<Person>> fillCitiesList() {		
		for(Long id : personsMap.keySet()) {			
			Person person = personsMap.get(id);
			String city = person.city();
			if(!citiesMap.containsKey(city)) {
				citiesMap.put(city, List.of(person) );
			} else {
				citiesMap.get(city).add(person);
			}			
		}
		return null;
	}


	@Override
	public String getGreetings(long id) {
		Person person = personsMap.get(id);		
		String name = person != null ? person.name() : "Unknown Guest";	
		return "Hello, " + name;
	}

	@Override
	public Person getPerson(long id) {
		Person person = personsMap.getOrDefault(id, null);
		return person;
	}
	@Override
	public List<Person> getPersonsByCity(String city) {
		List<Person> list = new ArrayList<>();
		for(Entry<Long, Person> entry : personsMap.entrySet()) {
			Person person = entry.getValue();
			if(person.city() == city) {
				list.add(person);
			}			
		}
		return list;
	}

	@Override
	public Person addPerson(Person person) {
		long id = person.id();
		Person res  = personsMap.putIfAbsent(id, person);
		if(res == null) {
			throw new IllegalStateException(id + " already exist"); 
		}		
		return res;		 
	}

	@Override
	public Person deletePerson(long id) {
		Person person = personsMap.remove(id);
		if(person == null) {
			throw new IllegalStateException(id + " not exist");
		}		
		return person;
	}

	@Override
	public Person updatePerson(Person person) {
		long id = person.id();
		deletePerson(id);
		addPerson(person);
		return null;
	}

}

