package com.skylabng.jaizexpress.staff.mapper;

import com.skylabng.jaizexpress.staff.StaffPayload;
import com.skylabng.jaizexpress.staff.model.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffMapper {
    StaffPayload StaffToPayload(Staff item);

    Staff payloadToStaff(StaffPayload payload);
}
