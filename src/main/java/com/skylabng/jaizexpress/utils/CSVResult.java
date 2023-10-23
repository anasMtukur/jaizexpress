package com.skylabng.jaizexpress.utils;

import com.skylabng.jaizexpress.station.StationPayload;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public record CSVResult(List<CSVRecordPayload> failed, List<StationPayload> succeeded) {
}
