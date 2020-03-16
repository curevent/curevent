package com.curevent.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements GrantedAuthority {

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
