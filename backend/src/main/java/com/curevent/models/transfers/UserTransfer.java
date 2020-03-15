package com.curevent.models.transfers;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserTransfer {

    private UUID id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String status;
    private String password;
}
