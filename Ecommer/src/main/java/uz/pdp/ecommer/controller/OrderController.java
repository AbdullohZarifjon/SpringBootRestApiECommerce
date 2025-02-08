package uz.pdp.ecommer.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommer.dto.OrderDTO;
import uz.pdp.ecommer.entity.Product;
import uz.pdp.ecommer.entity.User;
import uz.pdp.ecommer.service.JwtService;
import uz.pdp.ecommer.service.OrderService;
import uz.pdp.ecommer.service.ProductService;
import uz.pdp.ecommer.service.UserService;

@RestController
@RequestMapping("order")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public HttpEntity<?> getOrders(@RequestHeader("token") String token) {
        System.out.println(token);
        return orderService.getOrders(token);
    }

    @PostMapping
    public void createOrder(@RequestBody OrderDTO orderDTO, @RequestHeader("token") String token) {
        orderService.save(orderDTO, token);
    }


}
