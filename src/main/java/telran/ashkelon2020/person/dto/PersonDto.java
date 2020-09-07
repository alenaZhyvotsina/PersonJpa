package telran.ashkelon2020.person.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.ashkelon2020.person.model.Address;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,   // добавлять по имени (name) класса (type)
			 	include = JsonTypeInfo.As.PROPERTY,
			 	property = "type",
			 	visible = true)
@JsonSubTypes({
	@Type(value = ChildDto.class, name = "child"),
	@Type(value = EmployeeDto.class, name = "employee"),
	@Type(value = PersonDto.class, name = "person")
})
public class PersonDto {
	
	Integer id;
	String name;
	LocalDate birthDate;
	Address address;  //it is nesessary to do AddressDto and use it instead of Address

}
