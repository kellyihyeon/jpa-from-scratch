package jpa.jpasample;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

// 별도 리스너 등록
public class DuckListener {

    @PrePersist
    private void prePersist(Object obj) {
        System.out.println("DuckListener.perPersist obj = " + obj);
    }

    @PostPersist
    public void postPersist(Object obj) {
        System.out.println("DuckListener.postPersist obj = " + obj);
    }
}
