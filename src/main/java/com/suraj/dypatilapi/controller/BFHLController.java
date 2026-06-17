package com.suraj.dypatilapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.suraj.dypatilapi.dto.RequestDto;
import com.suraj.dypatilapi.service.BFHLService;

@RestController
public class BFHLController {

    @Autowired
    private BFHLService bfhlService;

    @PostMapping("/bfhl")
    public Map<String, Object> process(
            @RequestBody RequestDto request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId) {

        long startTime = System.currentTimeMillis();

        Map<String, Object> response = bfhlService.process(request);

        long endTime = System.currentTimeMillis();

        response.put("processing_time_ms", endTime - startTime);

        if (requestId != null) {
            response.put("request_id", requestId);
        }

        return response;
    }
}