package com.skylabng.jaizexpress.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResetPasswordPayload {
    private String resetCode;
    private String password;
}
