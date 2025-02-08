package uz.pdp.ecommer.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link uz.pdp.ecommer.entity.User}
 */
@Value
public class LoginDto implements Serializable {
    String username;
    String password;
}