package org.springboot.tradenow.Repository.Implementation;

import org.springboot.tradenow.Entity.Coin;
import org.springboot.tradenow.Entity.OrderItem;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.OrderType;
import org.springboot.tradenow.Entity.Order;


import java.util.List;

public interface OrderImpl {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long id) throws Exception;
    List<Order> getAllOrders(Long userId, OrderType orderType, String assetSymbol);
    Order processOrder(Coin coin, OrderType orderType, double quantity, User user);
}
