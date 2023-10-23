package com.skylabng.jaizexpress.subsidy.mapper;

import com.skylabng.jaizexpress.subsidy.SubsidyPayload;
import com.skylabng.jaizexpress.subsidy.model.Subsidy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubsidyMapper {
    Subsidy payloadToSubsidy(SubsidyPayload payload);

    SubsidyPayload subsidyToPayload( Subsidy subsidy );
}
