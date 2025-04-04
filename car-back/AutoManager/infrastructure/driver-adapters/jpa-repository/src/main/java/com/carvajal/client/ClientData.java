package com.carvajal.client;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Table(name = "users")
public class ClientData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("id")
    private Long id;
    @Column("fullName")
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}