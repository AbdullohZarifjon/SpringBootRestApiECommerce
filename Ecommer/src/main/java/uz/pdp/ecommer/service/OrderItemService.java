package uz.pdp.ecommer.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.ecommer.entity.User;
import uz.pdp.ecommer.repo.OrderItemRepository;

@Service
public class OrderItemService {
    private final JwtService jwtService;
    private final UserService userService;
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(JwtService jwtService, UserService userService, OrderItemRepository orderItemRepository) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.orderItemRepository = orderItemRepository;
    }

    public HttpEntity<?> getOrderItems(Integer orderId, String token) {
        String userName = jwtService.getUserName(token);
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            return ResponseEntity.ok(orderItemRepository.findByOrder_Id(orderId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
