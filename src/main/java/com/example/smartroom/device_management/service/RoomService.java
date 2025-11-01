package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.room.RoomCreateDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.dto.room.RoomStatisticsDTO;

/**
 * Interface dịch vụ quản lý phòng trong hệ thống Smart Room.
 * Cung cấp các thao tác CRUD và thống kê cho phòng.
 */
public interface RoomService {
    /**
     * Tạo mới một phòng.
     *
     * @param dto Dữ liệu tạo phòng.
     * @return Thông tin phòng đã tạo.
     * @throws BadRequestException Nếu phòng với ID đã tồn tại.
     */
    RoomDTO createRoom(RoomCreateDTO dto) throws BadRequestException;
    
    /**
     * Lấy thông tin phòng theo ID.
     *
     * @param id ID của phòng.
     * @return Thông tin phòng.
     * @throws NotFoundException Nếu không tìm thấy phòng.
     */
    RoomDTO getRoomById(String id) throws NotFoundException;
    
    /**
     * Lấy danh sách phòng phân trang.
     *
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách phòng.
     */
    Page<RoomDTO> getList(Pageable pageRequest);
    
    /**
     * Lấy danh sách phòng theo từ khóa tìm kiếm và phân trang.
     *
     * @param search Từ khóa tìm kiếm.
     * @param pageable Thông tin phân trang.
     * @return Danh sách phòng phù hợp.
     */
    Page<RoomDTO> getList(String search, Pageable pageable);
    
    /**
     * Xóa phòng theo ID.
     *
     * @param id ID của phòng.
     * @return Thông tin phòng đã xóa.
     * @throws NotFoundException Nếu không tìm thấy phòng.
     */
    RoomDTO deleteRoomById(String id) throws NotFoundException;
    
    /**
     * Đếm tổng số phòng.
     *
     * @return Số lượng phòng.
     */
    Long count();
    
    /**
     * Lấy thống kê của phòng theo ID.
     *
     * @param id ID của phòng.
     * @return Thống kê phòng.
     */
    RoomStatisticsDTO getRoomStatisticsById(String id);
}
