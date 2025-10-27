package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.data_type.DataTypeCreateDTO;
import com.example.smartroom.device_management.dto.data_type.DataTypeDTO;
import com.example.smartroom.device_management.entity.DataType;
import com.example.smartroom.device_management.mapper.DataTypeMapper;
import com.example.smartroom.device_management.repository.DataTypeRepository;
import com.example.smartroom.device_management.service.DataTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataTypeServiceImpl implements DataTypeService{
    
    private final DataTypeRepository dataTypeRepository;
    private final DataTypeMapper dataTypeMapper;

    @Override
    @Transactional
    public DataTypeDTO createDataType(DataTypeCreateDTO dto) {
        if (dataTypeRepository.findByCode(dto.getCode()).isPresent()) {
            throw new BadRequestException("Data type code " + dto.getCode() + " already exists");
        }

        DataType dataType = dataTypeMapper.toEntity(dto);
        return dataTypeMapper.toDTO(dataTypeRepository.save(dataType));
    }

    @Override
    public DataTypeDTO getDataTypeById(Long id) {
        DataType dataType = dataTypeRepository.findById(id).
            orElseThrow(() -> new NotFoundException("Data type id " + id + " not found"));

        return dataTypeMapper.toDTO(dataType);
    }

    @Override
    public DataTypeDTO getDataTypeByCode(String code) {
        DataType dataType = dataTypeRepository.findByCode(code).
            orElseThrow(() -> new NotFoundException("Data type code " + code + " not found"));

        return dataTypeMapper.toDTO(dataType);
    }

    @Override
    public Page<DataTypeDTO> getList(Pageable pageRequest) {
        return dataTypeRepository.findAll(pageRequest)
            .map(dataTypeMapper::toDTO);
    }

    @Override
    @Transactional
    public DataTypeDTO deleteDataTypeById(Long id) {
        DataType dataType = dataTypeRepository.findById(id).
            orElseThrow(() -> new NotFoundException("Data type id " + id + " not found"));

        dataTypeRepository.delete(dataType);
        return dataTypeMapper.toDTO(dataType);
    }
    
}
