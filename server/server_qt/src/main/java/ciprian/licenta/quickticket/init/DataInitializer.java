package ciprian.licenta.quickticket.init;

import ciprian.licenta.quickticket.entities.SystemAdmin;
import ciprian.licenta.quickticket.entities.UserRole;
import ciprian.licenta.quickticket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userService.existsByUsername("admin")) {
            SystemAdmin admin = new SystemAdmin(
                    "admin",
                    "admin",
                    "admin@example.com",
                    UserRole.SystemAdmin
            );

            userService.registerUser(admin);
            System.out.println("System admin account created.");
        } else {
            System.out.println("System admin account already exists.");
        }
    }
}
