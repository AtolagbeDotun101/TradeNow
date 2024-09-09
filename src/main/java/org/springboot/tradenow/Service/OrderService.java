package org.springboot.tradenow.Service;

import jakarta.transaction.Transactional;
import org.springboot.tradenow.Entity.*;
import org.springboot.tradenow.Enum.OrderStatus;
import org.springboot.tradenow.Enum.OrderType;
import org.springboot.tradenow.Repository.Implementation.OrderImpl;
import org.springboot.tradenow.Repository.OrderItemRepository;
import org.springboot.tradenow.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService  implements OrderImpl {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;


    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
        Order order = new Order();
        order.setUser(user);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTimestamp(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(()->new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrders(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    public OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(double quantity, Coin coin, User user) throws Exception {
        if(quantity <= 0){
            throw new Exception("Quantity should be greater than 0");
        }
        double buyPrice = coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,0);
        Order order = createOrder(user,orderItem,OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order,user);
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order saveOrder= orderRepository.save(order);
//        create asset
        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(user.getId(),coin.getId());
        if(oldAsset == null){
            assetService.createAsset(user,coin,orderItem.getQuantity());
        }else {
            assetService.updateAsset(oldAsset.getId(),quantity);
        }

        return saveOrder;
    }

    @Transactional
    public Order sellAsset(double quantity, Coin coin, User user) throws Exception {
        if(quantity <= 0){
            throw new Exception("Quantity should be greater than 0");
        }
        double sellPrice = coin.getCurrentPrice();
        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(),coin.getId());
        if(assetToSell != null) {
            double buyPrice = assetToSell.getBuyPrice();
            OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
            Order order = createOrder(user, orderItem, OrderType.SELL);
            orderItem.setOrder(order);

            if (assetToSell.getQuantity() > quantity) {
                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order saveOrder = orderRepository.save(order);
                walletService.payOrderPayment(order, user);

                Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
                if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updatedAsset.getId());

                }
                return saveOrder;
            }
            throw new Exception("Insufficient quantity to sell");
        }throw new Exception("Asset not found");
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, OrderType orderType, double quantity, User user)throws Exception {
        if(orderType.equals(OrderType.BUY)){
            buyAsset(quantity,coin,user);
        } else if (orderType.equals(OrderType.SELL)) {
            sellAsset(quantity,coin,user);
        }
        throw  new Exception("Invalid order type");
    }
}
