package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.dtos.ApiResponse;
import ciprian.licenta.quickticket.dtos.UserDTO;
import ciprian.licenta.quickticket.entities.User;
import ciprian.licenta.quickticket.services.UserService;
import ciprian.licenta.quickticket.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createUser(@RequestBody UserDTO userDTO) {
        User newUser = UserMapper.toEntity(userDTO);
        userService.createUser(newUser);
        return ResponseEntity.ok(new ApiResponse<>(true, "User created successfully!", null));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable UUID userId, @RequestBody UserDTO userDTO) {
        User updatedUser = UserMapper.toEntity(userDTO);
        userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully!", null));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully!", null));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable UUID userId) {
        User user = userService.findUserById(userId);
        UserDTO userDTO = UserMapper.toDTO(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully!", userDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully!", userDTOs));
    }
}
