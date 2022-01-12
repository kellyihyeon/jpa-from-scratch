package jpa.jpashop.repositoty;

import jpa.jpashop.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    // 관리자에게 필요한 기능
    public void save(Item item) {
        if (item.getId() == null) { // 식별자가 없다? == 새로운 엔티티 -> 영속화(persist)
            em.persist(item);
        } else {    // 파라미터로 넘어온 준영속 상태의 엔티티: 변경 감지 기능 동작 안함
            em.merge(item); // 수정(병합) - 준영속 상태의 엔티티를 수정할 때 사용!
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
