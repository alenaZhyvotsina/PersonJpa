package telran.ashkelon2020.person.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.person.dao.PersonRepository;
import telran.ashkelon2020.person.dto.PersonDto;
import telran.ashkelon2020.person.dto.PersonUpdateDto;
import telran.ashkelon2020.person.exceptions.PersonNotFoundException;
import telran.ashkelon2020.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public boolean addPerson(PersonDto personDto) {
		if(personRepository.existsById(personDto.getId())) {
			return false;
		}
		Person person = modelMapper.map(personDto, Person.class);
		personRepository.save(person);
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePerson(Integer id, PersonUpdateDto personUpdateDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		person.setName(personUpdateDto.getName());
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public List<PersonDto> findAllByName(String name) {	
		name = name.trim().toLowerCase();
		name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		Stream<Person> persons = personRepository.findByName(name);
		
		if(persons != null) {
			return persons.map(p -> modelMapper.map(p, PersonDto.class))
						  .collect(Collectors.toList());
		}	
		
		return null;
	}

	@Override
	public List<PersonDto> findAllByAges(int age1, int age2) {
		if(age1 > age2) {
			int temp = age1;
			age1 = age2;
			age2 = temp;
		}
		LocalDate date1 = LocalDate.now().minusYears(age2).plusDays(1);
		LocalDate date2 = LocalDate.now().minusYears(age1);
		List<Person> persons = personRepository.findByBirthDateBetween(date1, date2);
		
		return persons.stream().map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

}
