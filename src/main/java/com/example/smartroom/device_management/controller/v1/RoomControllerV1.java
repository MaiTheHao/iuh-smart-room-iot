package com.example.smartroom.device_management.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartroom.common.util.ApiResponse;
import com.example.smartroom.device_management.dto.room.RoomCreateDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomControllerV1 {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllRooms(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<RoomDTO> rooms = roomService.getList(pageRequest);

        ApiResponse<?> response = ApiResponse.success(
            org.springframework.http.HttpStatus.OK,
            "Fetched rooms successfully",
            rooms
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getRoomById(@PathVariable String id) {
        RoomDTO room = roomService.getRoomById(id);

        ApiResponse<RoomDTO> response = ApiResponse.success(
            org.springframework.http.HttpStatus.OK,
            "Fetched room successfully",
            room
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createRoom(@RequestBody @Valid RoomCreateDTO roomCreateDto) {
        RoomDTO createdRoom = roomService.createRoom(roomCreateDto);

        ApiResponse<RoomDTO> response = ApiResponse.success(
            org.springframework.http.HttpStatus.CREATED,
            "Room created successfully",
            createdRoom
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteRoom(@PathVariable String id) {
        RoomDTO deleted = roomService.deleteRoomById(id);

        ApiResponse<?> response = ApiResponse.success(
            org.springframework.http.HttpStatus.OK,
            "Room deleted successfully",
            deleted
        );

        return ResponseEntity.ok(response);
    }
}
