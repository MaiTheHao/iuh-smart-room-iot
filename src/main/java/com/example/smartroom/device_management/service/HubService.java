package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.hub.HubCreateDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;

public interface HubService {
    HubDTO createHub(HubCreateDTO dto);
    HubDTO getHubById(String id);
    Page<HubDTO> getList(Pageable pageRequest);
    Page<HubDTO> getListByRoomId(String roomId, Pageable pageRequest);
    HubDTO deleteHubById(String id);
    Long count();
    Long countByRoomId(String roomId);
}
