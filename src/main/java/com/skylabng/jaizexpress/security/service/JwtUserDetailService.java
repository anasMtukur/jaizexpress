package com.skylabng.jaizexpress.security.service;

//import com.skylabng.jaizexpress.role.RoleInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.role.RoleInternalAPI;
import com.skylabng.jaizexpress.role.RolePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class JwtUserDetailService implements UserDetailsService {
    @Autowired
    EndUserInternalAPI userAPI;

    @Autowired
    RoleInternalAPI roleAPI;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        EndUserPayload user =
                userAPI.getUserByEmailOrMobile(username, username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

        if (user.isBlocked() || user.isDeleted()) {
            throw new DisabledException("Your account has been disabled or deleted");
        }

        return toUser(user);
    }

    private User toUser(EndUserPayload user) {
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<RolePayload> roleList = roleAPI.findByUserId( user.id() );

        roleList.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority( String.valueOf( role.roleName() ) ));
        });

        return new User(user.username(), user.password(), authorities);
    }
}
