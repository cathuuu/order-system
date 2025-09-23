package com.example.authentication.entities;

import com.example.authentication.Role.RoleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name ="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false, length = 50, name ="username")
    String username;

    @Column(unique = true, nullable = false, length = 50, name ="password")
    String password;

    @Column(unique = true, nullable = false, length = 50, name ="email")
    String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    RoleEnum role;
}
