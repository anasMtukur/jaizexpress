package com.skylabng.jaizexpress.station.model;

import com.skylabng.jaizexpress.audit.AuditableEntity;
import com.skylabng.jaizexpress.enums.StationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "stations" )
public class Station extends AuditableEntity {
    @Basic(optional = false)
    @Column(name = "pos_index", nullable = false)
    private int posIndex;

    @Basic(optional = false)
    @Column(name = "zone_id", nullable = false)
    private UUID zoneId;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "addon_charge", nullable = false)
    private double addonCharge;

    @Basic(optional = false)
    @Column(name = "locality", nullable = false)
    private String locality;

    @Null
    @Basic
    @Column(name = "lat", nullable = true)
    private double lat;

    @Null
    @Basic
    @Column(name = "lon", nullable = true)
    private double lon;

    @Null
    @Basic
    @Column(name = "geo_hash", nullable = true)
    private String geoHash;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StationStatus status;
}
