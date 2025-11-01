package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.hub.HubCreateDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;
import com.example.smartroom.device_management.dto.hub.HubStatisticsDTO;

/**
 * Interface dịch vụ quản lý hub trong hệ thống Smart Room.
 * Cung cấp các thao tác CRUD và thống kê cho hub, liên kết với phòng.
 */
public interface HubService {
    /**
     * Tạo mới một hub.
     *
     * @param dto Dữ liệu tạo hub.
     * @return Thông tin hub đã tạo.
     * @throws NotFoundException Nếu phòng không tồn tại.
     * @throws BadRequestException Nếu hub với ID đã tồn tại.
     */
    HubDTO createHub(HubCreateDTO dto) throws NotFoundException, BadRequestException;
    
    /**
     * Lấy thông tin hub theo ID.
     *
     * @param id ID của hub.
     * @return Thông tin hub.
     * @throws NotFoundException Nếu không tìm thấy hub.
     */
    HubDTO getHubById(String id) throws NotFoundException;
    
    /**
     * Lấy danh sách hub phân trang.
     *
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách hub.
     */
    Page<HubDTO> getList(Pageable pageRequest);
    
    /**
     * Lấy danh sách hub theo ID phòng và phân trang.
     *
     * @param roomId ID của phòng.
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách hub trong phòng.
     */
    Page<HubDTO> getListByRoomId(String roomId, Pageable pageRequest);
    
    /**
     * Xóa hub theo ID.
     *
     * @param id ID của hub.
     * @return Thông tin hub đã xóa.
     * @throws NotFoundException Nếu không tìm thấy hub.
     */
    HubDTO deleteHubById(String id) throws NotFoundException;
    
    /**
     * Đếm tổng số hub.
     *
     * @return Số lượng hub.
     */
    Long count();
    
    /**
     * Đếm số hub theo ID phòng.
     *
     * @param roomId ID của phòng.
     * @return Số lượng hub trong phòng.
     * @throws NotFoundException Nếu phòng không tồn tại.
     */
    Long countByRoomId(String roomId) throws NotFoundException;
    
    /**
     * Lấy thống kê của hub theo ID.
     *
     * @param id ID của hub.
     * @return Thống kê hub.
     * @throws NotFoundException Nếu không tìm thấy hub.
     */
    HubStatisticsDTO getHubStatisticsById(String id) throws NotFoundException;
}
