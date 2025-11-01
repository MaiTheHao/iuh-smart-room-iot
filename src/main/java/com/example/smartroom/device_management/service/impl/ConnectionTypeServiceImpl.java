package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeCreateDTO;
import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeDTO;
import com.example.smartroom.device_management.entity.ConnectionType;
import com.example.smartroom.device_management.mapper.ConnectionTypeMapper;
import com.example.smartroom.device_management.repository.ConnectionTypeRepository;
import com.example.smartroom.device_management.service.ConnectionTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConnectionTypeServiceImpl implements ConnectionTypeService {
    
    private final ConnectionTypeRepository connectionTypeRepository;
    private final ConnectionTypeMapper connectionTypeMapper;

    @Override
    @Transactional
    public ConnectionTypeDTO createConnectionType(ConnectionTypeCreateDTO dto) {
        if (connectionTypeRepository.findByCode(dto.getCode()).isPresent()) {
            throw new BadRequestException("Connection type code " + dto.getCode() + " already exists");
        }

        ConnectionType connectionType = connectionTypeMapper.toEntity(dto);
        return connectionTypeMapper.toDTO(connectionTypeRepository.save(connectionType));
    }

    @Override
    public ConnectionTypeDTO getConnectionTypeById(Long id) {
        ConnectionType connectionType = connectionTypeRepository.findById(id).
            orElseThrow(() -> new NotFoundException("Connection type id " + id + " not found"));
            
        return connectionTypeMapper.toDTO(connectionType);
    }

    @Override
    public ConnectionTypeDTO getConnectionTypeByCode(String code) {
        ConnectionType connectionType = connectionTypeRepository.findByCode(code).
            orElseThrow(() -> new NotFoundException("Connection type code " + code + " not found"));

        return connectionTypeMapper.toDTO(connectionType);
    }


    @Override
    public Page<ConnectionTypeDTO> getList(Pageable pageRequest) {
        return connectionTypeRepository.findAll(pageRequest)
            .map(connectionTypeMapper::toDTO);
    }

    @Override
    @Transactional
    public ConnectionTypeDTO deleteConnectionTypeById(Long id) {
        ConnectionType connectionType = connectionTypeRepository.findById(id).
            orElseThrow(() -> new NotFoundException("Connection type id " + id + " not found"));

        connectionTypeRepository.delete(connectionType);
        return connectionTypeMapper.toDTO(connectionType);
    }    
}
