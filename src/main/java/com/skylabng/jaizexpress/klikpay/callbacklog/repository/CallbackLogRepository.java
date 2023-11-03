package com.skylabng.jaizexpress.klikpay.callbacklog.repository;

import com.skylabng.jaizexpress.klikpay.callbacklog.model.CallbackLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CallbackLogRepository extends JpaRepository<CallbackLog, UUID> {

}
