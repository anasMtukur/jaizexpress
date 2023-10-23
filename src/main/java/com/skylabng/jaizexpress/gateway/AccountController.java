package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.audit.RegisterMethod;
import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.exception.RegistrationFailedException;
import com.skylabng.jaizexpress.exception.UserAlreadyExistException;
import com.skylabng.jaizexpress.payload.*;
import com.skylabng.jaizexpress.role.RoleInternalAPI;
import com.skylabng.jaizexpress.enums.RoleName;
import com.skylabng.jaizexpress.role.RolePayload;
import com.skylabng.jaizexpress.security.service.JwtUserDetailService;
import com.skylabng.jaizexpress.security.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Account API", description = "API for registration, login and other account management endpoints")
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/account")
public class AccountController {
    private EndUserInternalAPI userAPI;
    private RoleInternalAPI roleAPI;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AccountController( EndUserInternalAPI userAPI, RoleInternalAPI roleAPI ){
        this.userAPI = userAPI;
        this.roleAPI = roleAPI;
    }

    @Operation(
            summary = "Authenticate A User",
            description = "Authenticate a user with given username and password. The response is a JSON containing JWT Token")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AccountCredentials authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken( userDetails );

        return ResponseEntity.ok(new AuthenticationResponse(token, userDetails));
    }

    @Operation(
            summary = "Register New User",
            description = "Create a new User with default role of USER. This endpoint should only be available to User Mobile App.")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(
            @RequestBody RegisterPayload payload) throws Exception {

        RegisterMethod registerBy = RegisterMethod.valueOf( payload.getRegisterBy() );
        String username = registerBy.equals( RegisterMethod.EMAIL ) ? payload.getEmail() : payload.getMobile();
        EndUserPayload entity = new EndUserPayload(
                    null,
                    payload.getFirstName(),
                    payload.getLastName(),
                    username,
                    payload.getEmail(),
                    payload.getMobile(),
                    passwordEncoder.encode(payload.getPassword()),
                    null, false, false, null, null, null
                );


        boolean exist = userAPI.checkUserExist(username, payload.getEmail(), payload.getMobile());
        if(exist){
            //Throw user exist exception
            throw new UserAlreadyExistException("Username", username);
        }

        entity = userAPI.save(entity);

        if( entity.id() == null ){
            //Throw Failed registration
            throw new RegistrationFailedException("User registration failed. Please try again.");
        }

        //Add Roles
        RolePayload roleEntity = new RolePayload( null, entity.id(), RoleName.valueOf( payload.getRole() ));
        roleAPI.addRole(roleEntity);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername( username );

        final String token = jwtTokenUtil.generateToken( userDetails );

        return ResponseEntity.ok(new AuthenticationResponse(token, userDetails));
    }

    @Operation(
            summary = "Verify Reset Code",
            description = "Check if a reset code is valid before allowing user to reset their password")
    @GetMapping( value = "/verify-reset-code/{reset-code}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity< EndUserPayload > verityResetCode(
            @PathVariable( name = "reset-code" ) String resetCode) throws Exception {

        EndUserPayload entity = userAPI.getUserByResetCode( resetCode );

        return ResponseEntity.ok( entity );
    }

    @Operation(
            summary = "Initiate forgot password",
            description = "This endpoint should be called when user forgot their password and want to reset it. " +
                    "A reset token and link will be generated and sent to the user via account email.")
    @PostMapping(value = "/forgot-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordPayload payload) throws Exception {

        EndUserPayload entity = userAPI.initiateForgotPassword( payload.getUsername() );

        //Send notification to username channel
        System.out.println( entity.resetToken() ); //Keep until notification works

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Reset Password",
            description = "Updates the user account with the new password. This should only be called if verify reset code is successful.")
    @PutMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordPayload payload) throws Exception {

        String password = passwordEncoder.encode( payload.getPassword() );
        EndUserPayload entity = userAPI.resetPassword( payload.getResetCode(), password );

        return ResponseEntity.ok().build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
