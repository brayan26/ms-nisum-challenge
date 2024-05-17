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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number", length = 13)
    private String number;
    @Column(name = "cityCode")
    private String cityCode;
    @Column(name = "countryCode")
    private String countryCode;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private UserEntity user;

    public static PhoneEntity create(String number, String cityCode, String countryCode, UserEntity user) {
        return new PhoneEntity(null, number, cityCode, countryCode, null);
    }
}
