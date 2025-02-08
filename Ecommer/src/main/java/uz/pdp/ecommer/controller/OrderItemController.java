package uz.pdp.ecommer.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommer.service.OrderItemService;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{orderId}")
    public HttpEntity<?> getOrderItem(@PathVariable Integer orderId, @RequestHeader String token) {
        return orderItemService.getOrderItems(orderId, token);
    }
}
