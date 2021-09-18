package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Address;
import errorhandling.ExceptionDTO;
import errorhandling.PersonNotFoundException;

public interface IPersonFacade {
    public PersonDTO addPerson(String fName, String lName, String phone, Address address);
    public PersonDTO deletePerson(int id) throws PersonNotFoundException;
    public PersonDTO getPerson(int id);
    public PersonsDTO getAllPersons();
    public PersonDTO editPerson(PersonDTO p);

}
