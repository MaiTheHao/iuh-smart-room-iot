package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeCreateDTO;
import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeDTO;

public interface ConnectionTypeService {
    ConnectionTypeDTO createConnectionType(ConnectionTypeCreateDTO dto);
    ConnectionTypeDTO getConnectionTypeById(Long id);
    ConnectionTypeDTO getConnectionTypeByCode(String code);
    Page<ConnectionTypeDTO> getList(Pageable pageRequest);
    ConnectionTypeDTO deleteConnectionTypeById(Long id);
}
