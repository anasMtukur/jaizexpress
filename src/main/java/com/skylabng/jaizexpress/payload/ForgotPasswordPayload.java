package com.skylabng.jaizexpress.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ForgotPasswordPayload {
    private String username;
}
