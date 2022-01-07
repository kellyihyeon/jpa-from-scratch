package jpa.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    @Column(name = "city")
    private String city;

    String street;

    String state;


    // 기본 생성자
    protected Address() {

    }

    // 생성자로만 값 설정 (수정자 만들지 않기)
    public Address(String city, String street, String state) {
        this.city = city;
        this.street = street;
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
