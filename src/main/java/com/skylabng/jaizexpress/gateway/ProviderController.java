package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.payload.AddStaffPayload;
import com.skylabng.jaizexpress.staff.StaffInternalAPI;
import com.skylabng.jaizexpress.staff.StaffPayload;
import com.skylabng.jaizexpress.exception.RegistrationFailedException;
import com.skylabng.jaizexpress.exception.UserAlreadyExistException;
import com.skylabng.jaizexpress.provider.ProviderExternalAPI;
import com.skylabng.jaizexpress.provider.ProviderPayload;
import com.skylabng.jaizexpress.payload.ProviderDetailsPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.role.RoleInternalAPI;
import com.skylabng.jaizexpress.enums.RoleName;
import com.skylabng.jaizexpress.role.RolePayload;
import com.skylabng.jaizexpress.utils.PagingUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@Tag(name = "Provider API", description = "API for managing service providers")
@RequestMapping("/api/providers")
public class ProviderController {
    private ProviderExternalAPI api;

    private EndUserInternalAPI userAPI;

    private RoleInternalAPI roleAPI;

    private StaffInternalAPI staffAPI;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ProviderController(
            ProviderExternalAPI api,
            EndUserInternalAPI userApi,
            RoleInternalAPI roleAPI,
            StaffInternalAPI staffAPI ) {
        this.userAPI = userApi;
        this.roleAPI = roleAPI;
        this.api = api;
        this.staffAPI = staffAPI;
    }

    @Operation(
            summary = "List All Providers",
            description = "Returns a paged list of all the service providers in the system. " +
                    "You can call this endpoint with page parameter to move between pages.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedPayload<ProviderPayload>> find(
            Principal principal,
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "search", required = false, defaultValue = StringUtils.EMPTY) String searchCriteria,
            @RequestParam(name = "searchtext", required = false, defaultValue = "") String searchText,
            @RequestParam(name = "pagesize", required = false, defaultValue = "1000") int pageSize,
            @RequestParam(name = "sortby", required = false, defaultValue = "date") String sortBy,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(name = "createdBy", required = false, defaultValue = "") String createdBy) throws Exception {

        PageRequest pageRequest = PagingUtil.getPageRequestObject(pageNumber, pageSize, sortBy, sortDirection);
        PagedPayload<ProviderPayload> response = api.getList(pageRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Add New Provider",
            description = "Create a new provider with a corresponding user that will have the role of PROVIDER_ADMIN")
    @PostMapping
    public ProviderPayload add(@RequestBody ProviderPayload o) throws UserAlreadyExistException, RegistrationFailedException {
        ProviderPayload entity = api.add(o);

        String username = o.email();
        String mobile = o.mobile();

        boolean exist = userAPI.checkUserExist( username, username, mobile );
        if(exist){
            throw new UserAlreadyExistException("Username", username);
        }

        String generatedPassword = "dicalemitoge";

        EndUserPayload user = new EndUserPayload(
                null,
                o.title(),
                "Provider",
                username,
                o.email(),
                o.mobile(),
                passwordEncoder.encode( generatedPassword ),
                null, false, false, null, null, null
        );
        user = userAPI.save(user);
        if( user.id() == null ){
            //Throw Failed registration
            throw new RegistrationFailedException("User registration failed. Please try again.");
        }

        //Add Roles
        RolePayload roleEntity = new RolePayload( null, user.id(), RoleName.PROVIDER_ADMIN );
        roleAPI.addRole(roleEntity);

        StaffPayload staff = new StaffPayload( null, user.id(), entity.id(), null, null );
        staffAPI.add( staff );

        return entity;
    }

    @Operation(
            summary = "Get Provider Details",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Provider ID") },
            description = "Get full details of a service provider with list of staff users and services")
    @GetMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ProviderDetailsPayload> getProviderDetails(
            Principal principal, @PathVariable( name = "id" ) String id) throws Exception {
        ProviderDetailsPayload response = api.findByIdWithUsersAndServices(UUID.fromString( id ) );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Add Provider Staff User",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Provider ID") },
            description = "Create a new staff user for provider with the given role")
    @PostMapping( value = "/{id}/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EndUserPayload> addProviderStaff(
            Principal principal,
            @PathVariable( name = "id" ) String id,
            @RequestBody AddStaffPayload payload ) throws Exception {

        String generatedPassword = "dicalemitoge";
        EndUserPayload endUser = new EndUserPayload(
                null,
                payload.firstName(),
                payload.lastName(),
                payload.username(),
                payload.username(),
                null,
                passwordEncoder.encode( generatedPassword ),
                null, false, false, null, null, null
        );

        endUser = userAPI.save( endUser );

        RolePayload roleEntity = new RolePayload( null, endUser.id(), RoleName.valueOf( payload.role() ) );
        roleAPI.addRole( roleEntity );

        StaffPayload staff = new StaffPayload( null, endUser.id(), UUID.fromString( id ), null, null );
        staffAPI.add( staff );

        return ResponseEntity.ok( endUser );
    }
}
