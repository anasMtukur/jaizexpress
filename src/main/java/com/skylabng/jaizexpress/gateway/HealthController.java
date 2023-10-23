package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.enduser.service.EndUserService;
import io.micrometer.common.util.internal.logging.AbstractInternalLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/health")
public class HealthController {
    private static final Logger LOG = LoggerFactory.getLogger(HealthController.class);
    @GetMapping
    public ResponseEntity<String> healthCheck() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        LOG.info("Health Check At: " + dtf.format(now));

        return ResponseEntity.ok().body("ok");
    }
}
