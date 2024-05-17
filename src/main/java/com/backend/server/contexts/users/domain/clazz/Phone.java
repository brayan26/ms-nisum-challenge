package com.backend.server.contexts.users.domain.clazz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Phone {
    @NotNull
    @NotBlank(message = "The number field should not be empty")
    private String number;
    @NotNull
    @NotBlank(message = "The cityCode field should not be empty")
    private String cityCode;
    @NotNull
    @NotBlank(message = "The countryCode field should not be empty")
    private String countryCode;

}
