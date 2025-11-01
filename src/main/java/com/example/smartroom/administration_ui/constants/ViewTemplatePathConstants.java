package com.example.smartroom.administration_ui.constants;

import lombok.experimental.UtilityClass;

/**
 * Hằng số tập trung cho đường dẫn template view.
 */
@UtilityClass
public class ViewTemplatePathConstants {

    /** Đường dẫn cơ bản cho tất cả các view */
    public static final String BASE_PATH = "pages/";

    /** Đường dẫn view liên quan đến Dashboard */
    @UtilityClass
    public static class Dashboard {
        public static final String BASE_PATH = ViewTemplatePathConstants.BASE_PATH + "dashboard/";
        public static final String INDEX = BASE_PATH + "index";
    }

    /** Đường dẫn view liên quan đến quản lý phòng */
    @UtilityClass
    public static class Rooms {
        public static final String BASE_PATH = ViewTemplatePathConstants.BASE_PATH + "rooms/";
        public static final String INDEX = BASE_PATH + "index";
        public static final String DETAIL = BASE_PATH + "[id]";
    }
}
