package com.skylabng.jaizexpress.payload;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "user" })
public class AuthenticationResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -8091879091924046844L;

    private String token;

    private UserDetails user;
}
