package com.example.smartroom.modules.room.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.core.common.vo.ApiResponseBody;
import com.example.smartroom.modules.room.dto.RoomLanDto;
import com.example.smartroom.modules.room.dto.UpdateRoomLanDto;
import com.example.smartroom.modules.room.service.RoomLanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
public class RoomLanControllerV1 {
    
    private final RoomLanService roomLanService;

    /**
     * Update hoặc tạo translation cho room theo langCode (Primary endpoint)
     * 
     * PUT /api/v1/translations/room/{roomId}/lang/{langCode}
     * {
     *   "name": "Phòng 101 - Cập nhật",
     *   "description": "Mô tả mới",
     *   "locationDescription": "Vị trí mới"
     * }
     */
    @PutMapping("/room/{roomId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<RoomLanDto>> upsertByRoomIdAndLangCode(
            @PathVariable Long roomId,
            @PathVariable String langCode,
            @RequestBody @Valid UpdateRoomLanDto dto) {
        RoomLanDto result = roomLanService.upsertByRoomIdAndLangCode(roomId, langCode, dto);
        return ResponseEntity.ok(ApiResponseBody.success("Translation updated successfully", result));
    }

    /**
     * Lấy tất cả translations của một room
     * 
     * GET /api/v1/translations/room/{roomId}
     */
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ApiResponseBody<List<RoomLanDto>>> getTranslationsByRoomId(
            @PathVariable Long roomId) {
        List<RoomLanDto> translations = roomLanService.getByRoomId(roomId);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translations));
    }

    /**
     * Lấy translation của room theo language code
     * 
     * GET /api/v1/translations/room/{roomId}/lang/{langCode}
     */
    @GetMapping("/room/{roomId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<RoomLanDto>> getTranslationByLanguage(
            @PathVariable Long roomId,
            @PathVariable String langCode) {
        RoomLanDto translation = roomLanService.getByRoomIdAndLangCode(roomId, langCode);
        return ResponseEntity.ok(ApiResponseBody.success("Fetched successfully", translation));
    }

    /**
     * Xóa một translation cụ thể của room
     * 
     * DELETE /api/v1/translations/room/{roomId}/lang/{langCode}
     */
    @DeleteMapping("/room/{roomId}/lang/{langCode}")
    public ResponseEntity<ApiResponseBody<Boolean>> deleteTranslation(
            @PathVariable Long roomId,
            @PathVariable String langCode) {
        roomLanService.deleteByRoomIdAndLangCode(roomId, langCode);
        return ResponseEntity.ok(ApiResponseBody.success("Translation deleted successfully", true));
    }
}
