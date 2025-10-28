package com.example.smartroom.administration_ui.constants;

import lombok.experimental.UtilityClass;

/**
 * Lớp chứa các hằng số đường dẫn đến các template view trong ứng dụng quản trị SmartRoom.
 * <p>
 * Các hằng số này được sử dụng để xác định vị trí các file giao diện cho các chức năng như Dashboard, Quản lý phòng, v.v.
 * </p>
 */
@UtilityClass
public class ViewTemplatePathConstants {
    
    // Base Path
    public static final String BASE_PATH = "pages/";

    // Dashboard
    public static final String DASHBOARD_INDEX = BASE_PATH + "dashboard/index";

    // Rooms
    public static final String ROOMS_INDEX = BASE_PATH + "rooms/index";
    public static final String ROOMS_DETAIL = BASE_PATH + "rooms/[id]";
}
