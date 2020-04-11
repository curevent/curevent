package com.curevent.models.transfers;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

    private String id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String status;
}
