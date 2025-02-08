package uz.pdp.ecommer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommer.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}