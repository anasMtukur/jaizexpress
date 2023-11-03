package com.skylabng.jaizexpress.klikpay.callbacklog.service;

import com.skylabng.jaizexpress.klikpay.callbacklog.CallbackLogExternalAPI;
import com.skylabng.jaizexpress.klikpay.callbacklog.CallbackLogInternalAPI;
import com.skylabng.jaizexpress.klikpay.callbacklog.CallbackLogPayload;
import com.skylabng.jaizexpress.klikpay.callbacklog.mapper.CallbackLogMapper;
import com.skylabng.jaizexpress.klikpay.callbacklog.model.CallbackLog;
import com.skylabng.jaizexpress.klikpay.callbacklog.repository.CallbackLogRepository;
import com.skylabng.jaizexpress.payload.PagedPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallbackLogService implements CallbackLogInternalAPI, CallbackLogExternalAPI {
    private final CallbackLogRepository repository;
    private final CallbackLogMapper mapper;

    public CallbackLogService(CallbackLogRepository repository, CallbackLogMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PagedPayload<CallbackLogPayload> getCallbackList(Pageable pageable) {
        Page<CallbackLog> page = this.repository.findAll(pageable);
        List<CallbackLogPayload> list = page.getContent()
                .stream()
                .map(mapper::callbackToPayload)
                .toList();

        return new PagedPayload<CallbackLogPayload>(list, page.getTotalPages(), page.getTotalElements());
    }

    @Override
    public CallbackLogPayload save(CallbackLogPayload payload) {
        return mapper.callbackToPayload(
                this.repository.saveAndFlush(mapper.payloadToCallback( payload )));
    }
}
