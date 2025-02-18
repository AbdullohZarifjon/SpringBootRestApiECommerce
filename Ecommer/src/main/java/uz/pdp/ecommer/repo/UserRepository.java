package uz.pdp.ecommer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommer.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);
}