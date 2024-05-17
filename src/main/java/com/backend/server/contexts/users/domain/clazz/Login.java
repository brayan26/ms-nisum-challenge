package com.backend.server.contexts.users.domain.clazz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Login {
    @NotNull
    @NotBlank(message = "The email field should not be empty")
    @Pattern(regexp = "^[\\w\\-.]+@([\\w-]+\\.){1,2}[\\w-]{2,4}$",
            message= "Invalid email format, [example@example.com, example22.ex@example.com, example@example.com.co"
    )
    private String email;
    @NotNull
    @NotBlank(message = "The password field should not be empty")
    private String password;
}
