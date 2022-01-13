package jpa.jpashop.repository.custom;

import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderSearch;

import java.util.List;

public interface CustomOrderRepository {

    public List<Order> search(OrderSearch orderSearch);
}
