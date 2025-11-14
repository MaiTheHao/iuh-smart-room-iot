package com.example.smartroom.modules.room.controller;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.device.dto.DeviceDto;
import com.example.smartroom.modules.room.dto.CreateRoomDto;
import com.example.smartroom.modules.room.dto.RoomDto;
import com.example.smartroom.modules.room.dto.UpdateRoomDto;
import com.example.smartroom.modules.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomControllerV1 {
    private final RoomService roomService;

    /**
     * Tạo room mới - CHỈ có floorId
     * Translations tạo riêng qua POST /api/v1/translations
     * 
     * Request body example:
     * {
     *   "floorId": 1
     * }
     */
    @PostMapping
    public ResponseEntity<ApiResponseBody<RoomDto>> create(
            @RequestBody @Valid CreateRoomDto createRoomDto) {
        RoomDto roomDto = roomService.create(createRoomDto);
        return ResponseEntity.ok(ApiResponseBody.success("Created successfully", roomDto));
    }

    /**
     * Lấy danh sách rooms với pagination và optional language filter
     * 
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param lang Language code (optional) - flatten translation fields vào response
     * 
     * Example: GET /api/v1/rooms?page=0&size=10&lang=vi
     * Response: {id, floorId, name, description, langCode}
     */
    @GetMapping
    public ResponseEntity<ApiResponseBody<PaginationInfo<RoomDto>>> getList(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String lang) {
        PaginationInfo<RoomDto> roomPage = roomService.getList(pageable, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", roomPage));
    }

    /**
     * Lấy room theo ID với optional language filter
     * 
     * @param id Room ID
     * @param lang Language code (optional) - flatten translation fields
     * 
     * Example: GET /api/v1/rooms/1?lang=en
     * Response: {id, floorId, name, description, locationDescription, langCode}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseBody<RoomDto>> getById(
            @PathVariable Long id,
            @RequestParam(required = false) String lang) {
        RoomDto roomDto = roomService.getById(id, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", roomDto));
    }

    /**
     * Update room - CHỈ update floorId
     * Translations update riêng qua PUT /api/v1/translations/:id
     * 
     * @param id Room ID
     * @param lang Language code (optional) - để flatten translation trong response
     * 
     * Request body example:
     * {
     *   "floorId": 2
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseBody<RoomDto>> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRoomDto updateRoomDto,
            @RequestParam(required = false) String lang) {
        RoomDto roomDto = roomService.update(id, updateRoomDto, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Updated successfully", roomDto));
    }

    /**
     * Xóa room (cascade delete tất cả translations)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseBody<Boolean>> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.ok(ApiResponseBody.success("Deleted successfully", true));
    }

    /**
     * Lấy danh sách devices theo room ID (con cấp 1)
     * 
     * @param roomId Room ID
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param lang Language code (optional) - flatten translation fields
     * 
     * Example: GET /api/v1/rooms/1/devices?page=0&size=10&lang=vi
     * Response: {id, roomId, model, mac, ip, name, description, locationDescription, langCode}
     */
    @GetMapping("/{roomId}/devices")
    public ResponseEntity<ApiResponseBody<PaginationInfo<DeviceDto>>> getDevicesByRoomId(
            @PathVariable Long roomId,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String lang) {
        PaginationInfo<DeviceDto> devicePage = roomService.getDevicesByRoomId(roomId, pageable, lang);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", devicePage));
    }
}
