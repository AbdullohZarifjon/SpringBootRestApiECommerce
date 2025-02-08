package uz.pdp.ecommer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.ecommer.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE :categoryId = 0 OR p.category.id = :categoryId")
    List<Product> findProductsByCategory(@Param("categoryId") int categoryId);
}