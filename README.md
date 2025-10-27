# Smart Room

Ứng dụng này là một phần của hệ thống lớn, chỉ đảm nhiệm việc thu thập, lưu trữ và trực quan hóa dữ liệu thiết bị IoT.

Ứng dụng quản lý thiết bị IoT, giám sát và điều khiển thông minh.

## Tính năng

-   Quản lý: Phòng > Hub > Thiết bị > Cảm biến
-   Thu thập dữ liệu cảm biến (nhiệt độ, độ ẩm, điện năng, ...)
-   Giám sát trạng thái, điều khiển thiết bị
-   Hỗ trợ WiFi, Zigbee, Bluetooth

## Công nghệ

-   **Spring Boot** (Java 17), **JPA**, **Thymeleaf**
-   **MySQL**, **Lombok**, **MapStruct**
-   **Maven** quản lý build

## Cấu hình

-   `application.yaml` (chung)
-   `application-dev.yaml` (dev)
-   `application-prod-war.yaml` (prod/Tomcat)

## Thiết lập cơ sở dữ liệu

Thư mục `setup` chứa các file SQL để khởi tạo và sinh dữ liệu mẫu cho database:

-   `init.sql`: Tạo cấu trúc bảng, quan hệ
-   `mock_data.sql`: Sinh dữ liệu mẫu cho các bảng

## Chạy ứng dụng

**Dev:**

```sh
./mvnw spring-boot:run
```

**Prod (WAR):**

```sh
./mvnw clean package -Pprod -DskipTests
```

Deploy `target/ROOT.war` lên Tomcat v10.
