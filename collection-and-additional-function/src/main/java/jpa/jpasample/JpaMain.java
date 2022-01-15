package jpa.jpasample;


import jpa.jpasample.domain.Order;
import jpa.jpasample.domain.OrderItem;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaMain {

    public static void main(String[] args) {
        // 동적 엔티티 그래프 연습을 위한 코드
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("entity-graph");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        EntityGraph<Order> entityGraph = em.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes("member");

        Map hints = new HashMap();
        String key = "javax.persistence.fetchgraph";
        hints.put(key, entityGraph);

        Order order = em.find(Order.class, 1L, hints);

        // subgraph 기능 구성하기
        EntityGraph<Order> graph = em.createEntityGraph(Order.class);
        graph.addAttributeNodes("member");
        Subgraph<OrderItem> orderItems = graph.addSubgraph("orderItems");
        orderItems.addAttributeNodes("item");   // OrderItem -> Item

        Map hints2 = new HashMap();
        hints2.put(key, graph);

        Order order2 = em.find(Order.class, 1L, hints2);

    }
}
