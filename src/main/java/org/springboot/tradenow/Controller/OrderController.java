package org.springboot.tradenow.Controller;

import org.springboot.tradenow.Entity.Coin;
import org.springboot.tradenow.Entity.Order;
import org.springboot.tradenow.Entity.User;
import org.springboot.tradenow.Enum.OrderType;
import org.springboot.tradenow.Helper.UserServiceImpl;
import org.springboot.tradenow.RequestDTO.CreateOrderRequest;
import org.springboot.tradenow.Service.CoinService;
import org.springboot.tradenow.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private  OrderService orderService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;


    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest req) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findCoinById(req.getCoinId());

        Order order = orderService.processOrder(coin,req.getOrderType(), req.getQuantity(), user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        }else throw new Exception("Unauthorized Request");
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("Authorization") String jwt,
                                                    @RequestParam(required = false)OrderType orderType,
                                                    @RequestParam(required = false)String assetSymbol )throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.getAllOrders(user.getId(),orderType,assetSymbol);
        return ResponseEntity.ok(orders);

    }



}
