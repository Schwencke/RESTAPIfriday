package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.Person;

import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.Date;
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


    @Override
    public PersonDTO deletePerson(int id) {
        return null;
    }

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("select p from Person p where p.id =:id",Person.class);
        query.setParameter("id", id);
        return PersonDTO.getDto(query.getSingleResult());

    }

    @Override
    public List<PersonsDTO> getAllPersons() {

            EntityManager em = emf.createEntityManager();
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> psl = query.getResultList();
            return PersonsDTO.getDtos(psl);
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

}
