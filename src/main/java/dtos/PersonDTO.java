package dtos;

import entities.Address;
import entities.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonDTO {
    private String fName;
    private String lName;
    private String phone;
    private Integer id;
    private Date created;
    private Date lastEdited;
    private String street;
    private String zip;
    private String city;

    public PersonDTO(String fName, String lName, String phone) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
    }

    public PersonDTO(String fName, String lName, String phone, Integer id, Date created, Date lastEdited, String street, String zip, String city) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    public PersonDTO(String fName, String lName, String phone, Integer id) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.id = id;
    }

    public PersonDTO(Person ps) {
        this.fName = ps.getFirstName();
        this.lName = ps.getLastName();
        this.phone = ps.getPhone();
        this.id = ps.getId();
    }

    public static PersonDTO getDto(Person ps){
        return new PersonDTO(ps.getFirstName(), ps.getLastName(), ps.getPhone());
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

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                '}';
    }
}
