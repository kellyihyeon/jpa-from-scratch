package jpa.model.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Zipcode {

    String zip;

    String plusFour;




    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPlusFour() {
        return plusFour;
    }

    public void setPlusFour(String plusFour) {
        this.plusFour = plusFour;
    }


}
