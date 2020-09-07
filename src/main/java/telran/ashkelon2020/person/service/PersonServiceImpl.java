package telran.ashkelon2020.person.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.ashkelon2020.person.dao.PersonRepository;
import telran.ashkelon2020.person.dto.ChildDto;
import telran.ashkelon2020.person.dto.CityPopulationDto;
import telran.ashkelon2020.person.dto.EmployeeDto;
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
	@Transactional  // = Atomic
	public boolean addPerson(PersonDto personDto) {
		
		if(personRepository.existsById(personDto.getId())) {
			return false;
		}
		
		Class<?> clazzDto = personDto.getClass();		
		String nameClassDto = clazzDto.getSimpleName();
		String nameClassModel = nameClassDto.substring(0, nameClassDto.length() - 3);
		
		try {
			Class<?> clazzModel = Class.forName("telran.ashkelon2020.person.model." + nameClassModel);
			Object entity = clazzModel.getConstructor().newInstance();
			//System.out.println("!!!!!!!!! " + entity.getClass().getName());
			Object person = modelMapper.map(personDto, entity.getClass());
			
			personRepository.save((Person)person);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		System.out.println("!! " + person.getClass().getName());
		
		Class<?> clazzModel = person.getClass();		
		String nameClassDto = clazzModel.getSimpleName() + "Dto";
		
		try {
			Class<?> clazzDto = Class.forName("telran.ashkelon2020.person.dto." + nameClassDto);
			Object entity = clazzDto.getConstructor().newInstance();
			
			return (PersonDto) modelMapper.map(person, entity.getClass());			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	@Transactional  // пока выполняется метод, удерживается соединение с БД 
					// и операции с БД идут как единое целое
	public PersonDto updatePerson(Integer id, PersonUpdateDto personUpdateDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		person.setName(personUpdateDto.getName());
		personRepository.save(person);
		//return modelMapper.map(person, PersonDto.class);
		
		Class<?> clazzModel = person.getClass();		
		String nameClassDto = clazzModel.getSimpleName() + "Dto";
		
		try {
			Class<?> clazzDto = Class.forName("telran.ashkelon2020.person.dto." + nameClassDto);
			Object entity = clazzDto.getConstructor().newInstance();
			
			return (PersonDto) modelMapper.map(person, entity.getClass());			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	@Transactional
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		personRepository.delete(person);
		//return modelMapper.map(person, PersonDto.class);
		
		Class<?> clazzModel = person.getClass();		
		String nameClassDto = clazzModel.getSimpleName() + "Dto";
		
		try {
			Class<?> clazzDto = Class.forName("telran.ashkelon2020.person.dto." + nameClassDto);
			Object entity = clazzDto.getConstructor().newInstance();
			
			return (PersonDto) modelMapper.map(person, entity.getClass());			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	@Transactional(readOnly = true)   // только чтобы удерживать соединение, но не транзакцию!
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

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {		
		city = city.trim().toLowerCase();
		city = Character.toUpperCase(city.charAt(0)) + city.substring(1);
		
		return personRepository.findByAddressCity(city)
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCityPopulation();
	}

	@Override
	public Iterable<PersonDto> findEmployeeBySalary(int min, int max) {
		return personRepository.findBySalaryNotNullAndBetween(min, max).stream()
				.map(p -> modelMapper.map(p, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PersonDto> getChildren() {
		return personRepository.findByKindergartenNotNull().stream()
				.map(p -> modelMapper.map(p, ChildDto.class))
				.collect(Collectors.toList());
	}


}
