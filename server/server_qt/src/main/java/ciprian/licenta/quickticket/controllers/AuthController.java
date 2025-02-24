package ciprian.licenta.quickticket.controllers;

import ciprian.licenta.quickticket.dtos.ApiResponse;
import ciprian.licenta.quickticket.dtos.EventManagerDTO;
import ciprian.licenta.quickticket.dtos.UserDTO;  // Use a generic UserDTO
import ciprian.licenta.quickticket.entities.EventManager;
import ciprian.licenta.quickticket.entities.User;
import ciprian.licenta.quickticket.entities.UserRole;
import ciprian.licenta.quickticket.security.JwtUtil;
import ciprian.licenta.quickticket.services.UserService;
import ciprian.licenta.quickticket.utils.EventManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody EventManagerDTO eventManagerDTO) {
        EventManager newEventManager = EventManagerMapper.toEntity(eventManagerDTO);
        newEventManager.setRole(UserRole.EventManager);

        userService.registerUser(newEventManager);

        return ResponseEntity.ok(new ApiResponse<>(true, "Event Manager registered successfully!", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );

            UserDetails userDetails = userService.loadUserByUsername(userDTO.getUsername());
            String token = jwtUtil.generateToken(userDetails);

            User user = userService.findByUsername(userDTO.getUsername());

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("role", "ROLE_" + user.getRole().name());
            responseData.put("userId", user.getId());

            return ResponseEntity.ok(new ApiResponse<>(true, "User logged in successfully!", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Invalid username or password", null));
        }
    }
}
