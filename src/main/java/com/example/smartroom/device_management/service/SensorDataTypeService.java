package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeCreateDTO;
import com.example.smartroom.device_management.dto.sensor_data_type.SensorDataTypeDTO;

import java.util.List;

/**
 * Interface định nghĩa các dịch vụ quản lý loại dữ liệu cảm biến trong hệ thống Smart Room.
 * Cung cấp các phương thức để tạo, truy vấn, kiểm tra và xóa loại dữ liệu cảm biến.
 */
public interface SensorDataTypeService {
    /**
     * Tạo mới một loại dữ liệu cảm biến dựa trên thông tin cung cấp.
     *
     * @param dto Đối tượng chứa thông tin cần thiết để tạo loại dữ liệu cảm biến.
     * @return Đối tượng DTO đại diện cho loại dữ liệu cảm biến đã được tạo.
     */
    SensorDataTypeDTO createSensorDataType(SensorDataTypeCreateDTO dto);

    /**
     * Lấy thông tin loại dữ liệu cảm biến theo ID của cảm biến và ID của loại dữ liệu.
     *
     * @param sensorId ID của cảm biến.
     * @param dataTypeId ID của loại dữ liệu.
     * @return DTO của loại dữ liệu cảm biến nếu tìm thấy, ngược lại ném ngoại lệ NotFoundException.
     */
    SensorDataTypeDTO getSensorDataTypeById(String sensorId, Long dataTypeId);

    /**
     * Lấy danh sách tất cả loại dữ liệu cảm biến với phân trang.
     *
     * @param pageRequest Thông tin phân trang.
     * @return Trang chứa danh sách DTO của các loại dữ liệu cảm biến.
     */
    Page<SensorDataTypeDTO> getList(Pageable pageRequest);

    /**
     * Lấy danh sách loại dữ liệu cảm biến theo ID của cảm biến với phân trang.
     *
     * @param sensorId ID của cảm biến.
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách DTO của các loại dữ liệu cảm biến liên quan đến cảm biến.
     */
    List<SensorDataTypeDTO> getListBySensorId(String sensorId, Pageable pageRequest);

    /**
     * Lấy danh sách loại dữ liệu cảm biến theo ID của loại dữ liệu với phân trang.
     *
     * @param dataTypeId ID của loại dữ liệu.
     * @param pageRequest Thông tin phân trang.
     * @return Danh sách DTO của các loại dữ liệu cảm biến thuộc loại dữ liệu cụ thể.
     */
    List<SensorDataTypeDTO> getListByDataTypeId(Long dataTypeId, Pageable pageRequest);

    /**
     * Kiểm tra xem loại dữ liệu cảm biến có tồn tại theo ID của cảm biến và ID của loại dữ liệu hay không.
     *
     * @param sensorId ID của cảm biến.
     * @param dataTypeId ID của loại dữ liệu.
     * @return true nếu tồn tại, false nếu không.
     */
    boolean existsBySensorIdAndDataTypeId(String sensorId, Long dataTypeId);

    /**
     * Xóa loại dữ liệu cảm biến theo ID của cảm biến và ID của loại dữ liệu.
     *
     * @param sensorId ID của cảm biến.
     * @param dataTypeId ID của loại dữ liệu.
     * @return DTO của loại dữ liệu cảm biến đã bị xóa.
     */
    SensorDataTypeDTO deleteSensorDataTypeById(String sensorId, Long dataTypeId);
}
