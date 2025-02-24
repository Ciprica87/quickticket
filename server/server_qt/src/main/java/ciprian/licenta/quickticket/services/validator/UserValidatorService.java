package ciprian.licenta.quickticket.services.validator;

import ciprian.licenta.quickticket.entities.User;
import ciprian.licenta.quickticket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorService {
    private final UserRepository userRepository;

    @Autowired
    public UserValidatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserDetails(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new IllegalStateException("Username is already in use!");
        }
        if (existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email is already in use!");
        }
    }

    public void validateUpdatedDetails(User existingUser, User updatedUser) {
        if (!existingUser.getUsername().equals(updatedUser.getUsername()) && existsByUsername(updatedUser.getUsername())) {
            throw new IllegalStateException("Username is already in use!");
        }
        if (!existingUser.getEmail().equals(updatedUser.getEmail()) && existsByEmail(updatedUser.getEmail())) {
            throw new IllegalStateException("Email is already in use!");
        }
    }

    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
