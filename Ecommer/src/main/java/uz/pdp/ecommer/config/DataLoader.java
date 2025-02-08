package uz.pdp.ecommer.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.ecommer.entity.Role;
import uz.pdp.ecommer.entity.User;
import uz.pdp.ecommer.enumeration.RoleName;
import uz.pdp.ecommer.repo.RoleRepository;
import uz.pdp.ecommer.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DataLoader(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        Role role = new Role(RoleName.ROLE_ADMIN);
//        roleRepository.save(role);
//        Role role2 = new Role(RoleName.ROLE_USER);
//        roleRepository.save(role2);
//        List<Role> roles = roleRepository.findAll();
//        User user = User.builder()
//                .roles(new ArrayList<>(List.of(roles.get(0))))
//                .username("a")
//                .password(passwordEncoder.encode("1"))
//                .fullName("Eshmat Toshmatov")
//                .build();
//        userRepository.save(user);
//        User user1 = User.builder()
//                .roles(new ArrayList<>(List.of(roles.get(1))))
//                .username("b")
//                .password(passwordEncoder.encode("1"))
//                .fullName("Hikmat Nusratov")
//                .build();
//        userRepository.save(user1);
    }
}
