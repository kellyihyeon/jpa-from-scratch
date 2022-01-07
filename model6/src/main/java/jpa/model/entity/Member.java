package jpa.model.entity;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    Period workPeriod;

    @Embedded
    Address homeAddress;

    @Embedded   // 컬럼명 중복!
    @AttributeOverrides({
            @AttributeOverride(name="city", column =@Column(name = "COMPANY_CITY")),
            @AttributeOverride(name="street", column = @Column(name = "COMPANY_STREET")),
            @AttributeOverride(name="state", column = @Column(name = "COMPANY_STATE")),
//            @AttributeOverride(name="zipcode", column = @Column(name = "COMPANY_ZIPCODE")),
//            @AttributeOverride(name="zip", column = @Column(name = "COMPANY_ZIP")),
//            @AttributeOverride(name="plusFour", column = @Column(name = "COMPANY_PLUSFOUR"))
    })
    Address companyAddress;

    @Embedded
    PhoneNumber phoneNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Address companyAddress) {
        this.companyAddress = companyAddress;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", workPeriod=" + workPeriod +
                ", homeAddress=" + homeAddress +
                ", companyAddress=" + companyAddress +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
