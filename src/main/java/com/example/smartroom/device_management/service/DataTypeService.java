package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.data_type.DataTypeCreateDTO;
import com.example.smartroom.device_management.dto.data_type.DataTypeDTO;

public interface DataTypeService {
    DataTypeDTO createDataType(DataTypeCreateDTO dto);
    DataTypeDTO getDataTypeById(Long id);
    DataTypeDTO getDataTypeByCode(String code);
    Page<DataTypeDTO> getList(Pageable pageRequest);
    DataTypeDTO deleteDataTypeById(Long id);
}
