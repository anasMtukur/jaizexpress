package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.payload.ZoneDetailPayload;
import com.skylabng.jaizexpress.station.StationExternalAPI;
import com.skylabng.jaizexpress.station.StationPayload;
import com.skylabng.jaizexpress.utils.CSVResult;
import com.skylabng.jaizexpress.utils.CsvReaderUtil;
import com.skylabng.jaizexpress.zone.ZoneExternalAPI;
import com.skylabng.jaizexpress.zone.ZonePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Zones", description = "API for managing zones and stations for provider services")
@RequestMapping("/api/zones")
public class ZoneController {
    private final ZoneExternalAPI api;

    private final StationExternalAPI stationAPI;

    public ZoneController(ZoneExternalAPI api, StationExternalAPI stationAPI) {
        this.api = api;
        this.stationAPI = stationAPI;
    }

    @Operation(
            summary = "Get Zone Details",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Zone ID") },
            description = "Get full details of zones with given ID including list of all stations in the zone.")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ZoneDetailPayload> getZoneDetails(
            Principal principal,
            @PathVariable( name = "id" ) String id ) throws Exception {
        return ResponseEntity.ok( api.getZoneDetails( UUID.fromString( id ) ) );
    }

    @Operation(
            summary = "Add New Zone",
            description = "Add new zone to the given provider service")
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ZonePayload> addSingleZone(Principal principal, @RequestBody ZonePayload payload ){
        ZonePayload entity = api.save( payload );
        return ResponseEntity.ok( entity );
    }

    @Operation(
            summary = "Add Single Station To Zone",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Zone ID") },
            description = "Get full details of zones with given ID including list of all stations in the zone.")
    @PostMapping( value="/{id}/stations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<StationPayload> addStationToZone(Principal principal, @RequestBody StationPayload payload ){
        StationPayload entity = stationAPI.save( payload );
        return ResponseEntity.ok( entity );
    }

    @Operation(
            summary = "Upload Stations From CSV",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "id", description = "Zone ID") },
            description = "Upload multiple stations to a zone from a CSV file.")
    @PostMapping( value="/{id}/stations/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<CSVResult> uploadStationsToZone(
            Principal principal,
            @PathVariable(name = "id") String zoneId,
            @RequestPart("file") MultipartFile file ) throws IOException {

        CSVResult processCSV = CsvReaderUtil.readZoneStationPayloadFromCSV(file, UUID.fromString( zoneId ));
        List<StationPayload> items = processCSV.succeeded();

        items = stationAPI.saveAll( items );
        return ResponseEntity.ok( new CSVResult( processCSV.failed(), items) );
    }
}
