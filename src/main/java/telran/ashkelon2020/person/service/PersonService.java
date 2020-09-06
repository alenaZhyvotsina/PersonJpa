package telran.ashkelon2020.person.service;

import java.util.List;

import telran.ashkelon2020.person.dto.PersonDto;
import telran.ashkelon2020.person.dto.PersonUpdateDto;

public interface PersonService {
	
	boolean addPerson(PersonDto personDto);
	
	PersonDto findPersonById(Integer id);
	
	PersonDto updatePerson(Integer id, PersonUpdateDto personUpdateDto);
	
	PersonDto deletePerson(Integer id);
	
	List<PersonDto> findAllByName(String name);
	
	List<PersonDto> findAllByAges(int age1, int age2);
	
}
