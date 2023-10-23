package com.skylabng.jaizexpress.payload;

import lombok.Data;

@Data
public class RegisterPayload {
    String firstName;
    String lastName;
    String email;
    String mobile;
    String role;
    String password;
    String registerBy;
}
