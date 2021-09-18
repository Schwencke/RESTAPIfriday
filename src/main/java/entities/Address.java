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
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "zip", nullable = false)
    private String zip;
    @Column(name = "city", nullable = false)
    private String city;
    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private List<Person> occupants;

    public Address() {
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
}