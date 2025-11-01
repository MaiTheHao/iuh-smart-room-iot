package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.sensor.SensorCreateDTO;
import com.example.smartroom.device_management.dto.sensor.SensorDTO;
import com.example.smartroom.device_management.dto.sensor.SensorStatisticsDTO;

/**
 * Interface dịch vụ quản lý cảm biến trong hệ thống Smart Room.
 * Cung cấp các thao tác CRUD và thống kê cho cảm biến, liên kết với thiết bị.
 */
public interface SensorService {
    /**
     * Tạo mới một cảm biến.
     *
     * @param dto Dữ liệu tạo cảm biến.
     * @return Thông tin cảm biến đã tạo.
     * @throws NotFoundException Nếu thiết bị không tồn tại.
     * @throws BadRequestException Nếu cảm biến với ID đã tồn tại.
     */
    SensorDTO createSensor(SensorCreateDTO dto) throws NotFoundException, BadRequestException;
    
    /**
     * Lấy thông tin cảm biến theo ID.
     *
     * @param id ID của cảm biến.
     * @return Thông tin cảm biến.
     * @throws NotFoundException Nếu không tìm thấy cảm biến.
     */
    SensorDTO getSensorById(String id) throws NotFoundException;
    
    /**
     * Lấy danh sách cảm biến phân trang.
     *
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách cảm biến.
     */
    Page<SensorDTO> getList(Pageable pageRequest);
    
    /**
     * Lấy danh sách cảm biến theo ID thiết bị và phân trang.
     *
     * @param deviceId ID của thiết bị.
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách cảm biến trong thiết bị.
     * @throws NotFoundException Nếu thiết bị không tồn tại.
     */
    Page<SensorDTO> getListByDeviceId(String deviceId, Pageable pageRequest) throws NotFoundException;
    
    /**
     * Xóa cảm biến theo ID.
     *
     * @param id ID của cảm biến.
     * @return Thông tin cảm biến đã xóa.
     * @throws NotFoundException Nếu không tìm thấy cảm biến.
     */
    SensorDTO deleteSensorById(String id) throws NotFoundException;
    
    /**
     * Đếm tổng số cảm biến.
     *
     * @return Số lượng cảm biến.
     */
    Long count();
    
    /**
     * Đếm số cảm biến theo ID thiết bị.
     *
     * @param deviceId ID của thiết bị.
     * @return Số lượng cảm biến trong thiết bị.
     * @throws NotFoundException Nếu thiết bị không tồn tại.
     */
    Long countByDeviceId(String deviceId) throws NotFoundException;
    
    /**
     * Đếm số cảm biến theo ID hub.
     *
     * @param hubId ID của hub.
     * @return Số lượng cảm biến trong hub.
     * @throws NotFoundException Nếu hub không tồn tại.
     */
    Long countByHubId(String hubId) throws NotFoundException;
    
    /**
     * Đếm số cảm biến theo ID phòng.
     *
     * @param roomId ID của phòng.
     * @return Số lượng cảm biến trong phòng.
     * @throws NotFoundException Nếu phòng không tồn tại.
     */
    Long countByRoomId(String roomId) throws NotFoundException;
    
    /**
     * Lấy thống kê của cảm biến theo ID.
     *
     * @param id ID của cảm biến.
     * @return Thống kê cảm biến.
     * @throws NotFoundException Nếu không tìm thấy cảm biến.
     */
    SensorStatisticsDTO getSensorStatisticsById(String id) throws NotFoundException;
}
