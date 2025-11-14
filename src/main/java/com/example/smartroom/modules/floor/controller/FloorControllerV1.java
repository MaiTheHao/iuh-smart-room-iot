package com.example.smartroom.modules.floor.controller;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.floor.dto.CreateFloorDto;
import com.example.smartroom.modules.floor.dto.FloorDto;
import com.example.smartroom.modules.floor.dto.UpdateFloorDto;
import com.example.smartroom.modules.floor.service.FloorService;
import com.example.smartroom.modules.room.dto.RoomDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/floors")
@RequiredArgsConstructor
public class FloorControllerV1 {
    private final FloorService floorService;

    /**
     * Tạo floor mới - CHỈ có level
     * Translations tạo riêng qua POST /api/v1/translations
     * 
     * Request body example:
     * {
     *   "level": 1
     * }
     */
    @PostMapping
    public ResponseEntity<ApiResponseBody<FloorDto>> createFloor(
            @RequestBody @Valid CreateFloorDto createFloorDto) {
        FloorDto floorDto = floorService.create(createFloorDto);
        return ResponseEntity.ok(ApiResponseBody.success("Created successfully", floorDto));
    }

    /**
     * Lấy danh sách floors với pagination và optional language filter
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param lang Language code (optional) - flatten translation fields vào response
     * 
     * Example: GET /api/v1/floors?page=0&size=10&lang=vi
     * Response: {id, level, name, description, langCode}
     */
    @GetMapping
    public ResponseEntity<ApiResponseBody<PaginationInfo<FloorDto>>> getFloors(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String lang) {
        PaginationInfo<FloorDto> floorPage = floorService.getList(pageable, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", floorPage));
    }

    /**
     * Lấy floor theo ID với optional language filter
     * 
     * @param id Floor ID
     * @param lang Language code (optional) - flatten translation fields
     * 
     * Example: GET /api/v1/floors/1?lang=en
     * Response: {id, level, name, description, locationDescription, langCode}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseBody<FloorDto>> getFloorById(
            @PathVariable Long id,
            @RequestParam(required = false) String lang) {
        FloorDto floorDto = floorService.getById(id, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", floorDto));
    }

    /**
     * Update floor - CHỈ update level
     * Translations update riêng qua PUT /api/v1/translations/:id
     * 
     * @param id Floor ID
     * @param lang Language code (optional) - để flatten translation trong response
     * 
     * Request body example:
     * {
     *   "level": 2
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseBody<FloorDto>> updateFloor(
            @PathVariable Long id,
            @RequestBody @Valid UpdateFloorDto updateFloorDto,
            @RequestParam(required = false) String lang) {
        FloorDto floorDto = floorService.update(id, updateFloorDto, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Updated successfully", floorDto));
    }

    /**
     * Xóa floor (cascade delete tất cả translations)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseBody<Boolean>> deleteFloor(@PathVariable Long id) {
        floorService.delete(id);
        return ResponseEntity.ok(ApiResponseBody.success("Deleted successfully", true));
    }

    /**
     * Lấy danh sách rooms theo floor ID (con cấp 1)
     * 
     * @param floorId Floor ID
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param lang Language code (optional) - flatten translation fields
     * 
     * Example: GET /api/v1/floors/1/rooms?page=0&size=10&lang=vi
     * Response: {id, floorId, name, description, locationDescription, langCode}
     */
    @GetMapping("/{floorId}/rooms")
    public ResponseEntity<ApiResponseBody<PaginationInfo<RoomDto>>> getRoomsByFloorId(
            @PathVariable Long floorId,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String lang) {
        PaginationInfo<RoomDto> roomPage = floorService.getRoomsByFloorId(floorId, pageable, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", roomPage));
    }
}
