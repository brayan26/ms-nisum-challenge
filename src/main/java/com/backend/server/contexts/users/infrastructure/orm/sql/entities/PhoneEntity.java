package com.backend.server.contexts.users.infrastructure.orm.sql.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "phones")
public class PhoneEntity {
    @Id
    @Column(name = "number", length = 13)
    private String number;
    @Column(name = "cityCode")
    private String cityCode;
    @Column(name = "countryCode")
    private String countryCode;
    @Column(name = "user_id")
    private String userId;


    public static PhoneEntity create(String number, String cityCode, String countryCode, String userId) {
        return new PhoneEntity(number, cityCode, countryCode, userId);
    }
}
