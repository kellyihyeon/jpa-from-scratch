package com.jpa.tutorial;

import javax.persistence.*;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")),
        @AttributeOverride(name ="name", column = @Column(name = "MEMBER_NAME"))

})
public class Member extends BaseEntity {

    // id, name 상속

    private String email;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
