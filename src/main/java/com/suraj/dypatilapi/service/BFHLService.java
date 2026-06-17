package com.suraj.dypatilapi.service;

import java.util.Map;

import com.suraj.dypatilapi.dto.RequestDto;

public interface BFHLService {

    Map<String, Object> process(RequestDto request);
}