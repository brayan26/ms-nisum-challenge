package com.backend.server.contexts.users.domain.clazz;

import com.backend.server.contexts.users.domain.dto.UserSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String id;
    @NotNull
    @NotBlank(message = "The name field should not be empty")
    private String name;
    @NotNull
    @NotBlank(message = "The email field should not be empty")
    @Pattern(regexp = "^[\\w\\-.]+@([\\w-]+\\.){1,2}[\\w-]{2,4}$",
            message= "Invalid email format, [example@example.com, example22.ex@example.com, example@example.com.co"
    )
    private String email;
    @NotNull
    @NotBlank(message = "The password field should not be empty")
    private String password;
    private List<Phone> phones = Collections.emptyList();
    private Boolean isActive;
    private String token;
    private Date created;
    private Date modified;
    private Date lastLogin;

    public static User create(String id, String name, String email, String password, List<Phone> phones,
                              boolean active, String token, Date created, Date modified, Date lastLogin) {
        return new User(id, name, email, password, phones, active, token, created, modified, lastLogin);
    }

    public UserSerializer serialize() {
        return new ModelMapper().map(this, UserSerializer.class);
    }
}
