package uz.pdp.ecommer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommer.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_Id(Integer userId);
}