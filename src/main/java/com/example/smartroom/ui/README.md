# Smart Room UI - AdminLTE + Thymeleaf Template

## 📋 Tổng Quan

Phần UI của dự án Smart Room được xây dựng dựa trên:

-   **AdminLTE 3.2.0** - Dashboard Admin Template responsive
-   **Thymeleaf** - Server-side template engine
-   **Bootstrap 4.6.1** - CSS Framework
-   **jQuery 3.6.0** - JavaScript Library
-   **Font Awesome 5.15.4** - Icon Library

## 🏗️ Kiến Trúc Thư Mục

```
ui/
├── controller/              # Controllers điều khiển các trang UI
│   ├── DashboardController.java
│   ├── FloorController.java
│   ├── RoomController.java
│   ├── DeviceController.java
│   ├── SensorController.java
│   └── RootController.java
└── [models, services sẽ được thêm sau]

resources/
├── templates/              # Thymeleaf templates
│   ├── layouts/
│   │   └── main.html       # Layout chính
│   ├── components/         # Components tái sử dụng
│   │   ├── header.html     # Navbar
│   │   ├── sidebar.html    # Sidebar Menu
│   │   └── footer.html     # Footer
│   ├── pages/              # Nội dung các trang
│   │   ├── dashboard/
│   │   ├── floors/
│   │   ├── rooms/
│   │   ├── devices/
│   │   └── sensors/
│   └── error/              # Error pages
│       ├── 404.html
│       └── 500.html
├── static/                 # Static files
│   ├── css/
│   │   └── app.css         # Custom styles
│   ├── js/
│   │   └── app.js          # Custom scripts
│   └── images/             # Images & Icons
└── application*.yaml       # Configuration files
```

## 🎨 Tính Năng UI

### 1. **Dashboard**

-   Hiển thị thống kê tổng quát (Tầng, Phòng, Thiết bị, Cảm biến)
-   Biểu đồ động (Nhiệt độ, Độ ẩm) sử dụng Chart.js

### 2. **Quản lý Tầng**

-   Danh sách tầng với pagination
-   Tạo, chỉnh sửa, xóa tầng
-   Xem danh sách phòng trong tầng

### 3. **Quản lý Phòng**

-   Danh sách phòng theo tầng
-   Tạo phòng mới
-   Xem danh sách thiết bị trong phòng

### 4. **Quản lý Thiết bị**

-   Danh sách thiết bị
-   Tạo, chỉnh sửa, xóa thiết bị
-   Hỗ trợ gateway và thiết bị thường

### 5. **Quản lý Cảm biến**

-   Danh sách cảm biến
-   Tạo cảm biến mới
-   Liên kết cảm biến với thiết bị và loại

## 🚀 Routing

### Các Route Chính

| Route               | Method | Mô tả               |
| ------------------- | ------ | ------------------- |
| `/dashboard`        | GET    | Trang chủ Dashboard |
| `/floors`           | GET    | Danh sách tầng      |
| `/floors/create`    | GET    | Form tạo tầng       |
| `/floors/{id}/edit` | GET    | Form chỉnh sửa tầng |
| `/floors/{id}`      | GET    | Chi tiết tầng       |
| `/rooms`            | GET    | Danh sách phòng     |
| `/rooms/create`     | GET    | Form tạo phòng      |
| `/devices`          | GET    | Danh sách thiết bị  |
| `/devices/create`   | GET    | Form tạo thiết bị   |
| `/sensors`          | GET    | Danh sách cảm biến  |
| `/sensors/create`   | GET    | Form tạo cảm biến   |

## 🎯 Controllers

### DashboardController

```java
@GetMapping("/dashboard")
- Hiển thị trang Dashboard chính
```

### FloorController

```java
@GetMapping           - Danh sách tầng
@GetMapping("/create") - Form tạo tầng
@GetMapping("/{id}") - Chi tiết tầng
@GetMapping("/{id}/edit") - Form chỉnh sửa
```

Tương tự cho: RoomController, DeviceController, SensorController

## 🎨 Components

### header.html

-   Navbar với icon notifications
-   User account dropdown
-   Fullscreen toggle

### sidebar.html

-   Menu chính với các module
-   Expandable submenus
-   Icons cho mỗi item

### footer.html

-   Thông tin phiên bản
-   Copyright

## 💅 Custom Styles

### app.css (Tệp CSS tuỳ chỉnh)

-   Gradient backgrounds cho header và sidebar
-   Hover effects cho cards
-   Table styling
-   Form styling
-   Alert styling
-   Responsive design

## 🔧 Configuration

### application.yaml

```yaml
spring.thymeleaf:
    cache: false
    encoding: UTF-8
    prefix: classpath:/templates/
    suffix: .html
    mode: HTML
```

### application-dev.yaml

```yaml
server:
    port: 8080
spring:
    jpa:
        hibernate.ddl-auto: update
    thymeleaf:
        cache: false
logging:
    level: DEBUG
```

## 📦 Dependencies

Tất cả dependencies đã được thêm vào `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>nz.net.ultraq.thymeleaf</groupId>
    <artifactId>thymeleaf-layout-dialect</artifactId>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>AdminLTE</artifactId>
    <version>3.2.0</version>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>4.6.1</version>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>font-awesome</artifactId>
    <version>5.15.4</version>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.6.0</version>
</dependency>
```

## 🌐 Static Resources

### CSS Files

-   WebJars Bootstrap: `/webjars/bootstrap/4.6.1/css/bootstrap.min.css`
-   WebJars Font Awesome: `/webjars/font-awesome/5.15.4/css/all.min.css`
-   WebJars AdminLTE: `/webjars/AdminLTE/3.2.0/dist/css/adminlte.min.css`
-   Custom: `/css/app.css`

### JavaScript Files

-   WebJars jQuery: `/webjars/jquery/3.6.0/jquery.min.js`
-   WebJars Bootstrap: `/webjars/bootstrap/4.6.1/js/bootstrap.bundle.min.js`
-   WebJars AdminLTE: `/webjars/AdminLTE/3.2.0/dist/js/adminlte.min.js`
-   WebJars Chart.js: `/webjars/AdminLTE/3.2.0/plugins/chart.js/Chart.min.js`
-   Custom: `/js/app.js`

## 📱 Responsive Design

-   Mobile-first approach
-   Breakpoints: xs, sm, md, lg, xl
-   Sidebar collapse trên mobile
-   Table responsive

## 🔐 Security

-   CSRF protection (tích hợp sẵn Spring Security)
-   XSS prevention (Thymeleaf auto-escapes)
-   Input validation (client-side + server-side)

## 📚 Usage

### Thêm trang mới

1. Tạo Controller:

```java
@Controller
@RequestMapping("/new-page")
public class NewPageController {
    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "New Page");
        return "pages/new-page/index";
    }
}
```

2. Tạo Template:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{layouts/main}">
	<body>
		<div layout:fragment="content">
			<!-- Nội dung trang -->
		</div>
	</body>
</html>
```

3. Thêm vào Sidebar (components/sidebar.html)

## ✨ Best Practices

1. **Controllers**: Mỗi module có một controller riêng
2. **Templates**: Sử dụng layout và components để tái sử dụng code
3. **Static Files**: Sử dụng WebJars cho libraries, `/static` cho custom assets
4. **Naming**: Tuân theo convention (camelCase cho biến, kebab-case cho files)
5. **Comments**: Có documentation cho các controllers
6. **Error Handling**: Custom error pages (404, 500)

## 🔄 AJAX Integration

File `app.js` cung cấp helper functions:

```javascript
// Show alerts
showAlert(message, type);

// API calls
apiCall(method, endpoint, data, callback);

// Form submit handling
$(form).on('submit', ...);
```

## 📝 Thymeleaf Syntax

### Template Expressions

```html
<!-- Variable substitution -->
<p th:text="${pageTitle}"></p>

<!-- Links -->
<a th:href="@{/dashboard}">Dashboard</a>

<!-- Conditions -->
<div th:if="${condition}">...</div>

<!-- Loops -->
<tr th:each="item : ${items}">
	<td th:text="${item.name}"></td>
</tr>

<!-- Fragments -->
<div th:replace="~{components/header :: header}"></div>

<!-- Layout inheritance -->
<html layout:decorate="~{layouts/main}">
	<div layout:fragment="content">...</div>
</html>
```

## 🚀 Deployment

### Dev Mode

```bash
mvn spring-boot:run
```

### Production Mode

```bash
mvn clean package -Pprod
java -jar target/smartroom-0.0.1-SNAPSHOT.jar
```

## 📋 TODO

-   [ ] Thêm User authentication/authorization
-   [ ] Implement real-time data updates (WebSocket)
-   [ ] Thêm chart libraries (ApexCharts, ECharts)
-   [ ] Improve mobile UI
-   [ ] Thêm internationalization (i18n)
-   [ ] Performance optimization
-   [ ] Unit tests cho controllers
-   [ ] E2E tests

## 🤝 Contributing

Khi thêm tính năng mới:

1. Tuân theo kiến trúc hiện tại
2. Thêm documentation
3. Test trước khi commit
4. Cập nhật README nếu cần

---

**Version**: 1.0.0  
**Last Updated**: 2024-01-15
