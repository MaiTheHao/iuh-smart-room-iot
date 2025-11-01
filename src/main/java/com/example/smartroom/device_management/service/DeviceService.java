package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.device.DeviceCreateDTO;
import com.example.smartroom.device_management.dto.device.DeviceDTO;
import com.example.smartroom.device_management.dto.device.DeviceStatisticsDTO;

/**
 * Interface dịch vụ quản lý thiết bị trong hệ thống Smart Room.
 * Cung cấp các thao tác CRUD và thống kê cho thiết bị, liên kết với hub và loại kết nối.
 */
public interface DeviceService {
    /**
     * Tạo mới một thiết bị.
     *
     * @param dto Dữ liệu tạo thiết bị.
     * @return Thông tin thiết bị đã tạo.
     * @throws NotFoundException Nếu hub hoặc loại kết nối không tồn tại.
     * @throws BadRequestException Nếu thiết bị với ID đã tồn tại.
     */
    DeviceDTO createDevice(DeviceCreateDTO dto) throws NotFoundException, BadRequestException;
    
    /**
     * Lấy thông tin thiết bị theo ID.
     *
     * @param id ID của thiết bị.
     * @return Thông tin thiết bị.
     * @throws NotFoundException Nếu không tìm thấy thiết bị.
     */
    DeviceDTO getDeviceById(String id) throws NotFoundException;
    
    /**
     * Lấy danh sách thiết bị phân trang.
     *
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách thiết bị.
     */
    Page<DeviceDTO> getList(Pageable pageRequest);
    
    /**
     * Lấy danh sách thiết bị theo ID hub và phân trang.
     *
     * @param hubId ID của hub.
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách thiết bị trong hub.
     * @throws NotFoundException Nếu hub không tồn tại.
     */
    Page<DeviceDTO> getListByHubId(String hubId, Pageable pageRequest) throws NotFoundException;
    
    /**
     * Xóa thiết bị theo ID.
     *
     * @param id ID của thiết bị.
     * @return Thông tin thiết bị đã xóa.
     * @throws NotFoundException Nếu không tìm thấy thiết bị.
     */
    DeviceDTO deleteDeviceById(String id) throws NotFoundException;
    
    /**
     * Đếm tổng số thiết bị.
     *
     * @return Số lượng thiết bị.
     */
    Long count();
    
    /**
     * Đếm số thiết bị theo ID hub.
     *
     * @param hubId ID của hub.
     * @return Số lượng thiết bị trong hub.
     * @throws NotFoundException Nếu hub không tồn tại.
     */
    Long countByHubId(String hubId) throws NotFoundException;
    
    /**
     * Đếm số thiết bị theo ID phòng.
     *
     * @param roomId ID của phòng.
     * @return Số lượng thiết bị trong phòng.
     * @throws NotFoundException Nếu phòng không tồn tại.
     */
    Long countByRoomId(String roomId) throws NotFoundException;
    
    /**
     * Lấy thống kê của thiết bị theo ID.
     *
     * @param id ID của thiết bị.
     * @return Thống kê thiết bị.
     * @throws NotFoundException Nếu không tìm thấy thiết bị.
     */
    DeviceStatisticsDTO getDeviceStatisticsById(String id) throws NotFoundException;
}
