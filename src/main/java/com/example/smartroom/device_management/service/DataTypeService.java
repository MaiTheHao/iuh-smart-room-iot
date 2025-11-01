package com.example.smartroom.device_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.smartroom.device_management.dto.data_type.DataTypeCreateDTO;
import com.example.smartroom.device_management.dto.data_type.DataTypeDTO;

/**
 * Interface này cung cấp các phương thức để tạo, truy vấn và xóa loại dữ liệu.
 */
public interface DataTypeService {
    /**
     * Tạo mới một loại dữ liệu dựa trên thông tin từ DTO.
     *
     * @param dto Đối tượng chứa thông tin cần thiết để tạo loại dữ liệu.
     * @return Đối tượng DTO đại diện cho loại dữ liệu vừa được tạo.
     */
    DataTypeDTO createDataType(DataTypeCreateDTO dto);

    /**
     * Lấy thông tin loại dữ liệu theo ID.
     *
     * @param id ID của loại dữ liệu cần lấy.
     * @return Đối tượng DTO đại diện cho loại dữ liệu tương ứng.
     */
    DataTypeDTO getDataTypeById(Long id);

    /**
     * Lấy thông tin loại dữ liệu theo mã code duy nhất.
     *
     * @param code Mã code duy nhất của loại dữ liệu.
     * @return Đối tượng DTO đại diện cho loại dữ liệu tương ứng.
     */
    DataTypeDTO getDataTypeByCode(String code);

    /**
     * Lấy danh sách phân trang các loại dữ liệu.
     *
     * @param pageRequest Thông tin phân trang (số trang, kích thước trang, sắp xếp).
     * @return Đối tượng Page chứa danh sách DTO của các loại dữ liệu.
     */
    Page<DataTypeDTO> getList(Pageable pageRequest);

    /**
     * Xóa loại dữ liệu theo ID. Phương thức này có thể thực hiện xóa mềm (soft delete)
     * và trả về thông tin của loại dữ liệu đã xóa.
     *
     * @param id ID của loại dữ liệu cần xóa.
     * @return Đối tượng DTO đại diện cho loại dữ liệu đã được xóa.
     */
    DataTypeDTO deleteDataTypeById(Long id);
}
