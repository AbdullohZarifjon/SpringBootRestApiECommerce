package uz.pdp.ecommer.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link uz.pdp.ecommer.entity.User}
 */
@Value
public class UserDto implements Serializable {
    Integer id;
    String username;
    String password;
    String fullName;
}