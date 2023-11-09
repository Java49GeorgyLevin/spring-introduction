package telran.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import telran.spring.Person;
@Service
public class GreetingsServiceImpl implements GreetingsService {
	Person p123 = new Person(123l, "David", "Tel Aviv");
	Person p124 = new Person(124l, "Sara", "Bnei Brak");
	Person p125 = new Person(125l, "Rivka", "Petah Tikva");
	Person p126 = new Person(126l, "Izhak", "Petah Tikva");
	Map<Long, Person> personsMap = new HashMap<>(Map.of(123l, p123, 124l, p124, 125l, p125, 126l, p126));

	@Override
	public String getGreetings(long id) {
		Person person = personsMap.get(id);		
		String name = person != null ? person.name() : "Unknown Guest";	
		return "Hello, " + name;
	}

	@Override
	public Person getPerson(long id) {
		Person person = personsMap.get(id);
		if(person == null) {
			throw new IllegalStateException(id + " not exists"); 
		}
		return person;
	}
	@Override
	public List<Person> getPersonsByCity(String city) {
		return personsMap.values().stream()
		.filter(p -> p.city().endsWith(city))
		.toList();

	}

	@Override
	public Person addPerson(Person person) {
		long id = person.id();
		Person res  = personsMap.putIfAbsent(id, person);
		if(res != null) {
			throw new IllegalStateException(id + " already exist"); 
		}
		return person;		 
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

