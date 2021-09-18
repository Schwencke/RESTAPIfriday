package entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "address")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String street;
    private String zip;
    private String city;

    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    List<Person> personlist;

    public Address() {
    }

    public Address(String street, String zip, String city, List<Person> personlist) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.personlist = personlist;
    }

    public Address(String street, String zip, String city) {
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    public void addPersonToAddress(Person person){
        this.personlist.add(person);
        if (person != null){
            person.getAddress().addPersonToAddress(person);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Person> getPersonlist() {
        return personlist;
    }

    public void setPersonlist(List<Person> personlist) {
        this.personlist = personlist;
    }
}