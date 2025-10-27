package com.example.smartroom.device_management.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.common.util.ApiResponse;
import com.example.smartroom.device_management.dto.data_type.DataTypeCreateDTO;
import com.example.smartroom.device_management.dto.data_type.DataTypeDTO;
import com.example.smartroom.device_management.service.DataTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/data-types")
@RequiredArgsConstructor
public class DataTypeControllerV1 {
    private final DataTypeService dataTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllDataTypes(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<DataTypeDTO> dataTypes = dataTypeService.getList(pageRequest);

        ApiResponse<?> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched data types successfully",
            dataTypes
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getDataTypeById(@PathVariable Long id) {
        DataTypeDTO dataType = dataTypeService.getDataTypeById(id);

        ApiResponse<DataTypeDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched data type successfully",
            dataType
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<?>> getDataTypeByCode(@PathVariable String code) {
        DataTypeDTO dataType = dataTypeService.getDataTypeByCode(code);

        ApiResponse<DataTypeDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Fetched data type by code successfully",
            dataType
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createDataType(@RequestBody @Valid DataTypeCreateDTO dto) {
        DataTypeDTO createdDataType = dataTypeService.createDataType(dto);

        ApiResponse<DataTypeDTO> response = ApiResponse.success(
            HttpStatus.CREATED,
            "Data type created successfully",
            createdDataType
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteDataType(@PathVariable Long id) {
        DataTypeDTO deletedDataType = dataTypeService.deleteDataTypeById(id);

        ApiResponse<DataTypeDTO> response = ApiResponse.success(
            HttpStatus.OK,
            "Data type deleted successfully",
            deletedDataType
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
