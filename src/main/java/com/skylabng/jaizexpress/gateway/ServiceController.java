package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.payload.ServiceDetailsPayload;
import com.skylabng.jaizexpress.service.ServiceExternalAPI;
import com.skylabng.jaizexpress.service.ServicePayload;
import com.skylabng.jaizexpress.subsidy.SubsidyExternalAPI;
import com.skylabng.jaizexpress.subsidy.SubsidyInternalAPI;
import com.skylabng.jaizexpress.subsidy.SubsidyPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@Tag(name = "Services API", description = "API for managing provider services")
@RequestMapping("/api/services")
public class ServiceController {
    private final ServiceExternalAPI api;
    private final SubsidyInternalAPI subsidyAPI;

    public ServiceController(ServiceExternalAPI api, SubsidyInternalAPI subsidyAPI){
        this.api = api;
        this.subsidyAPI = subsidyAPI;
    }

    @Operation(
            summary = "Add new service",
            description = "Add new service to provider")
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ServicePayload> addService(Principal principal, @RequestBody ServicePayload payload){
        //Checks Here: Must Be Sys_Admin / Org_Admin
        return ResponseEntity.ok(api.addService( payload ));
    }

    @Operation(
            summary = "Get Service Details",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Service ID") },
            description = "Get full details of a service with list of zones and subsidies")
    @GetMapping( value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ServiceDetailsPayload> getServiceDetails(
            Principal principal, @PathVariable(name = "id") String id ) throws Exception {
        ServiceDetailsPayload payload = api.getServiceDetails( UUID.fromString( id ) );
        return ResponseEntity.ok( payload );
    }

    @Operation(
            summary = "Add Subsidy To Service",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Service ID") },
            description = "Create a new subsidy group under service with given ID.")
    @PostMapping( value = "/{id}/subsidy")
    public ResponseEntity<SubsidyPayload> addSubsidy(
            Principal principal,
            @PathVariable(name = "id") String serviceId,
            @RequestBody SubsidyPayload payload ) throws Exception {
        SubsidyPayload entity = subsidyAPI.save( payload );
        return ResponseEntity.ok(entity);
    }
}
