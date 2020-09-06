package telran.ashkelon2020.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.person.model.Person;

@Service
public interface PersonRepository extends JpaRepository<Person, Integer> {

	Stream<Person> findByName(String name);
		
	List<Person> findByBirthDateBetween(LocalDate date1, LocalDate date2);

}
