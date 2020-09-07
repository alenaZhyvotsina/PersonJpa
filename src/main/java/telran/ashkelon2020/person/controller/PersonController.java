package telran.ashkelon2020.person.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.person.dto.CityPopulationDto;
import telran.ashkelon2020.person.dto.PersonDto;
import telran.ashkelon2020.person.dto.PersonUpdateDto;
import telran.ashkelon2020.person.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	PersonService personService;
	
	@PostMapping
	public boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}
	
	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}
	
	@PutMapping("/{id}")
	public PersonDto updatePerson(@PathVariable Integer id, @RequestBody PersonUpdateDto personUpdateDto) {
		return personService.updatePerson(id, personUpdateDto);
	}
	
	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.deletePerson(id);
	}
	
	@GetMapping("/name/{name}")
	public List<PersonDto> findAllPersonsById(@PathVariable String name) {
		return personService.findAllByName(name);
	}
	
	@GetMapping("/age/{age1}/{age2}")
	public List<PersonDto> findAllPersonsByAgeRange(@PathVariable int age1, @PathVariable int age2) {
		return personService.findAllByAges(age1, age2);
	}
	
	@GetMapping("/city/{city}")
	public Iterable<PersonDto> findPersonsByCity(@PathVariable String city) {
		return personService.findPersonsByCity(city);
	}
	
	@GetMapping("/population/city")
	public Iterable<CityPopulationDto> getCityPopulation() {
		return personService.getCitiesPopulation();
	}
	
	@GetMapping("/employees/salary/{min}/{max}")
	public Iterable<PersonDto> findEmployeeBySalary(@PathVariable Integer min,
							@PathVariable Integer max) {
		return personService.findEmployeeBySalary(min, max);
	}
	
	@GetMapping("/children")
	public Iterable<PersonDto> getChildren() {
		return personService.getChildren();
	}

}
