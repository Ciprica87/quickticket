package ciprian.licenta.quickticket.utils;

import ciprian.licenta.quickticket.dtos.UserDTO;
import ciprian.licenta.quickticket.entities.User;
import ciprian.licenta.quickticket.entities.UserRole;

public class UserMapper {

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(convertToUserRole(dto.getRole()));
        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        return dto;
    }

    private static UserRole convertToUserRole(String roleStr) {
        if (roleStr == null || roleStr.isEmpty()) {
            return null;
        }

        try {
            return UserRole.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid role: " + roleStr, e);
        }
    }
}
