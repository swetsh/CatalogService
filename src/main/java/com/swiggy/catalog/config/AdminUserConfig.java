package com.swiggy.catalog.config;
import com.swiggy.catalog.model.User;
import com.swiggy.catalog.repository.UserRepository;
import com.swiggy.catalog.utils.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            User adminUser = userRepository.findByUsername("admin");

            if (adminUser == null) {
                Location adminLocation = new Location("Admin Location");
                String encodedPassword = passwordEncoder.encode("adminpassword");
                User admin = new User("admin", encodedPassword, adminLocation);
                userRepository.save(admin);
                System.out.println("Admin user created.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
