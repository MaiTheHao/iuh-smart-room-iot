package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeCreateDTO;
import com.example.smartroom.device_management.dto.connection_type.ConnectionTypeDTO;

/**
 * Interface cung cấp các dịch vụ quản lý loại kết nối trong hệ thống Smart Room.
 * Bao gồm các thao tác tạo, truy vấn, liệt kê và xóa loại kết nối.
 */
public interface ConnectionTypeService {
    /**
     * Tạo mới một loại kết nối dựa trên dữ liệu cung cấp.
     *
     * @param dto Dữ liệu tạo loại kết nối.
     * @return DTO của loại kết nối đã được tạo.
     */
    ConnectionTypeDTO createConnectionType(ConnectionTypeCreateDTO dto);

    /**
     * Lấy thông tin loại kết nối theo ID.
     *
     * @param id ID của loại kết nối cần truy vấn.
     * @return DTO của loại kết nối tương ứng.
     */
    ConnectionTypeDTO getConnectionTypeById(Long id);

    /**
     * Lấy thông tin loại kết nối theo mã code.
     *
     * @param code Mã code của loại kết nối cần truy vấn.
     * @return DTO của loại kết nối tương ứng.
     */
    ConnectionTypeDTO getConnectionTypeByCode(String code);

    /**
     * Lấy danh sách loại kết nối theo phân trang.
     *
     * @param pageRequest Thông tin phân trang (số trang, kích thước trang).
     * @return Danh sách DTO của loại kết nối dưới dạng phân trang.
     */
    Page<ConnectionTypeDTO> getList(Pageable pageRequest);

    /**
     * Xóa loại kết nối theo ID và trả về thông tin của nó.
     *
     * @param id ID của loại kết nối cần xóa.
     * @return DTO của loại kết nối đã bị xóa.
     */
    ConnectionTypeDTO deleteConnectionTypeById(Long id);
}
