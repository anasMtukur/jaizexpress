package com.skylabng.jaizexpress.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class AccountCredentials implements Serializable {
    private String username;
    private String password;
}