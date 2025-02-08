package uz.pdp.ecommer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommer.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}