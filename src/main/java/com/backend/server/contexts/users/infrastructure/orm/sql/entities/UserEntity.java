package com.backend.server.contexts.users.infrastructure.orm.sql.entities;

import com.backend.server.contexts.users.domain.clazz.Phone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneEntity> phones;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "token")
    private String token;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    public static UserEntity create(String id, String name, String email, String password,
                                    List<Phone> phones, boolean isActive, String token) {
        LocalDateTime now = LocalDateTime.now();
        Date today = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        List<PhoneEntity> phoneEntities = phones.stream().map(phone ->
                PhoneEntity.create(phone.getNumber(), phone.getCityCode(), phone.getCountryCode(), id)).toList();
        return new UserEntity(id, name, email, password, phoneEntities, isActive, token, today, null, today);
    }

}
