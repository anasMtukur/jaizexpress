package com.skylabng.jaizexpress.utils;

import com.skylabng.jaizexpress.enums.StationStatus;
import com.skylabng.jaizexpress.station.StationPayload;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CsvReaderUtil {

    public static CSVResult readZoneStationPayloadFromCSV( MultipartFile file, UUID zoneId ) throws IOException {
        List<StationPayload> payloads = new ArrayList<>();
        List<CSVRecordPayload> failed = new ArrayList<>();

        CSVParser records = CSVFormat.EXCEL.builder()
                .setSkipHeaderRecord( true )
                .build()
                .parse( new InputStreamReader( file.getInputStream() ) );

        List<CSVRecord> csvRecords = records.getRecords();

        for (CSVRecord record : csvRecords) {
            String index = record.get(0);
            String name  = record.get(1);
            String addon = record.get(2);
            String locality = record.get(3);
            String lat = record.get(4);
            String lon = record.get(5);

            if(addon.equalsIgnoreCase( "addon_charge" )){
                continue;
            }

            try {
                double addonCharge = Double.parseDouble( addon );
                double latitude = Double.parseDouble( lat );
                double longitude = Double.parseDouble( lon );
                String geoHash = GeoHashUtil.generateGeoHash(latitude, longitude) ;

                payloads.add(
                        new StationPayload(
                                null,
                                zoneId,
                                name,
                                addonCharge,
                                StationStatus.OPEN,
                                locality,
                                Integer.parseInt(index),
                                latitude,
                                longitude,
                                geoHash
                        )
                );
            }catch ( Exception e ){
                System.out.println( "Skipped: "+name );
                failed.add(
                        new CSVRecordPayload(
                                index,
                                name,
                                addon,
                                locality,
                                lat,
                                lon,
                                e.getMessage()
                        ) );
            }
        }

        return new CSVResult( failed, payloads );
    }


}

