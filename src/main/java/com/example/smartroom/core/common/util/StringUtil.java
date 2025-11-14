package com.example.smartroom.core.common.util;

import lombok.experimental.UtilityClass;
import java.util.Locale;
import java.util.Objects;

/**
 * Cung cấp các hàm tiện ích xử lý chuỗi cho hệ thống Smart Room.
 * Bao gồm chuẩn hóa chuỗi, kiểm tra rỗng, so sánh an toàn và giá trị mặc định.
 */
@UtilityClass
public class StringUtil {

    /**
     * Chuẩn hóa chuỗi: loại bỏ khoảng trắng đầu/cuối và chuyển về chữ thường.
     *
     * @param str Chuỗi đầu vào.
     * @return Chuỗi đã chuẩn hóa, trả về chuỗi rỗng nếu đầu vào là null.
     */
    public String normalize(String str) {
        return str == null ? "" : str.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * Kiểm tra chuỗi có rỗng hoặc chỉ chứa khoảng trắng hay không.
     *
     * @param str Chuỗi cần kiểm tra.
     * @return true nếu chuỗi rỗng hoặc chỉ chứa khoảng trắng, ngược lại trả về false.
     */
    public boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * So sánh hai chuỗi không phân biệt hoa thường, xử lý an toàn với giá trị null.
     *
     * @param a Chuỗi thứ nhất.
     * @param b Chuỗi thứ hai.
     * @return true nếu hai chuỗi bằng nhau (không phân biệt hoa thường), ngược lại trả về false.
     */
    public boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        return a.equalsIgnoreCase(b);
    }

    /**
     * So sánh hai chuỗi theo kiểu equals, xử lý an toàn với giá trị null.
     *
     * @param a Chuỗi thứ nhất.
     * @param b Chuỗi thứ hai.
     * @return true nếu hai chuỗi bằng nhau, ngược lại trả về false.
     */
    public boolean equalsSafe(String a, String b) {
        return Objects.equals(a, b);
    }

    /**
     * Trả về giá trị mặc định nếu chuỗi đầu vào là null.
     *
     * @param str Chuỗi đầu vào.
     * @param defaultValue Giá trị mặc định trả về nếu str là null.
     * @return str nếu khác null, ngược lại trả về defaultValue.
     */
    public String defaultIfNull(String str, String defaultValue) {
        return str == null ? defaultValue : str;
    }

    /**
     * Trả về giá trị mặc định nếu chuỗi đầu vào rỗng hoặc chỉ chứa khoảng trắng.
     *
     * @param str Chuỗi đầu vào.
     * @param defaultValue Giá trị mặc định trả về nếu str rỗng hoặc chỉ chứa khoảng trắng.
     * @return str nếu không rỗng, ngược lại trả về defaultValue.
     */
    public String defaultIfBlank(String str, String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }
}
