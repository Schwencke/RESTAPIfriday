package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Address;
import entities.Person;
import errorhandling.PersonNotFoundException;

import javax.persistence.*;
import java.util.List;

public class PersonFacade implements IPersonFacade{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
            Person ps = new Person(fName, lName, phone);
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(ps);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new PersonDTO(ps.getFirstName(), ps.getLastName(), ps.getPhone(), ps.getId());
        }


    public PersonDTO addPersonWithAdress(String fName, String lName, String phone, String srt, String zp, String ct) {
        EntityManager em = emf.createEntityManager();
        Address address;
            TypedQuery<Long> query = em.createQuery("select count(a) from Address a where a.street =:street", Long.class);
            query.setParameter("street", srt);
            long count = query.getSingleResult();
            if (count >= 1){
                TypedQuery<Address> query1 = em.createQuery("select a from Address a where a.street =:street", Address.class);
                query1.setParameter("street", srt);
                address = query1.getSingleResult();
            } else {
                address = new Address(srt,zp,ct);
            }

        Person ps = new Person(fName, lName, phone,address);
        address.addOccupants(ps);

        try {
            em.getTransaction().begin();
            em.persist(address);
            em.persist(ps);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(ps);
    }


    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {

        EntityManager em = emf.createEntityManager();
        Person p;
        try {
            em.getTransaction().begin();
            p = em.find(Person.class, id);
            if (p == null) {
                throw new PersonNotFoundException("Could not delete, provided id does not exist");
            }
            em.remove(p);
            em.getTransaction().commit();
        }  finally { em.close();
        }
        return PersonDTO.getDto(p);

    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person ps;
        ps = em.find(Person.class, id);
        if (ps == null){
            throw new PersonNotFoundException("No person with provided id "+ id + " ,was found");
        }
        return PersonDTO.getDto(ps);

    }

//    @Override
//    public List<PersonsDTO> getAllPersons() {
//
//            EntityManager em = emf.createEntityManager();
//            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
//            List<Person> psl = query.getResultList();
//            return PersonsDTO.getDtos(psl);
//    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        return new PersonsDTO(persons);
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        EntityManager em = emf.createEntityManager();
        Person ps;
        try {
            em.getTransaction().begin();
            ps = em.find(Person.class, p.getId());
            ps = ps.fromDTO(p,ps);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return PersonDTO.getDto(ps);

    }

    public static void main(String[] args) {
        populate();
    }

    private static void populate() {
        EntityManager em = emf.createEntityManager();
        Person et = new Person("Thomas", "Overgaard", "11");
        Person to = new Person("Thomas", "Overgaard", "111");
        Person tre = new Person("Thomas", "Overgaard", "111");
        Person fire = new Person("Thomas", "Overgaard", "111");
        Person fem = new Person("Thomas", "Overgaard", "111");

        try {
          em.getTransaction().begin();
          em.persist(et);
          em.persist(to);
          em.persist(tre);
          em.persist(fire);
          em.persist(fem);
          em.getTransaction().commit();
        }
         finally {
            em.close();
        }


    }

}
