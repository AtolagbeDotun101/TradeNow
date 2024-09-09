package org.springboot.tradenow.RequestDTO;

import lombok.Data;
import org.springboot.tradenow.Enum.OrderType;

@Data
public class CreateOrderRequest {
   private String coinId;
   private double quantity;
    private OrderType orderType;
}
