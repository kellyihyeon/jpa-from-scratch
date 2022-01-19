package jpa.jpashop.repositoty;

import jpa.jpashop.domain.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BoardRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public void flush() {
        em.flush();
    }
}
