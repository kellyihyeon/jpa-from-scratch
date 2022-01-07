package jpa.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    @Column(name = "city")
    String city;

    String street;

    String state;

//    @Embedded
//    Zipcode zipcode;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    public Zipcode getZipcode() {
//        return zipcode;
//    }
//
//    public void setZipcode(Zipcode zipcode) {
//        this.zipcode = zipcode;
//    }
}
