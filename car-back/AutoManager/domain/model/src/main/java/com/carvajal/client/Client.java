package com.carvajal.client;

import com.carvajal.client.properties.Email;
import com.carvajal.client.properties.FullName;
import com.carvajal.client.properties.Password;
import com.carvajal.client.properties.Role;
import com.carvajal.commons.properties.CreatedAt;
import com.carvajal.commons.properties.Id;
import com.carvajal.commons.properties.State;
import com.carvajal.commons.properties.UpdatedAt;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Client {
    private Id id;
    private FullName fullName;
    private Email email;
    private Password password;
    private Role role;
    private State state;
    private CreatedAt createdAt;
    private UpdatedAt updatedAt;

    public Client(Id id, FullName fullName, Email email, Password password, Role role, State state, CreatedAt createdAt, UpdatedAt updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
