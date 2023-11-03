package com.skylabng.jaizexpress.klikpay.callbacklog.mapper;

import com.skylabng.jaizexpress.klikpay.callbacklog.CallbackLogPayload;
import com.skylabng.jaizexpress.klikpay.callbacklog.model.CallbackLog;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface CallbackLogMapper {
    CallbackLogPayload callbackToPayload(CallbackLog model);
    CallbackLog payloadToCallback(CallbackLogPayload payload);
}
