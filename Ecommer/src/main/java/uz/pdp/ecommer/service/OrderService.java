package uz.pdp.ecommer.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.ecommer.dto.OrderDTO;
import uz.pdp.ecommer.entity.Order;
import uz.pdp.ecommer.entity.OrderItem;
import uz.pdp.ecommer.entity.Product;
import uz.pdp.ecommer.entity.User;
import uz.pdp.ecommer.enumeration.Status;
import uz.pdp.ecommer.repo.OrderItemRepository;
import uz.pdp.ecommer.repo.OrderRepository;

import java.time.LocalDate;

@Service
public class OrderService {
    private final JwtService jwtService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(JwtService jwtService, UserService userService, ProductService productService,
                        OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void save(OrderDTO orderDTO, String token) {
        String userName = jwtService.getUserName(token);

        User user = userService.getUserByUserName(userName);
        Order order = Order.builder()
                .date(LocalDate.now())
                .status(Status.IN_PROGRESS)
                .user(user)
                .build();
        orderRepository.save(order);
        double total = 0;
        for (Integer integer: orderDTO.getProducts().keySet()) {
            Product product = productService.getProductById(integer);
            total += product.getPrice() * orderDTO.getProducts().get(integer);
            OrderItem orderItem = new OrderItem(order, product, orderDTO.getProducts().get(integer), (double) (product.getPrice() * orderDTO.getProducts().get(integer)));
            orderItemRepository.save(orderItem);
        }
        order.setTotal(total);
        order.setStatus(Status.SUCCESS);
        orderRepository.save(order);
    }

    public HttpEntity<?> getOrders(String token) {
        String userName = jwtService.getUserName(token);
        User user = userService.getUserByUserName(userName);
        return ResponseEntity.ok(orderRepository.findByUser_Id(user.getId()));
    }
}
