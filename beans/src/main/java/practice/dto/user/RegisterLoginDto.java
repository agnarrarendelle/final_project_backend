package practice.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterLoginDto {

    @NotBlank
    @Size(min = 4, max = 15, message = "username must between 4 and 15")
    String username;

    @NotBlank
    @Size(min = 8, max = 20, message = "password must between 8 and 20")
    String password;
}
