# Quy Trình Phát Triển Tính Năng Xác Thực Cơ Bản (Spring Boot + PostgreSQL + JWT)

## Phần 1: Nền tảng và Cơ sở dữ liệu (Base Setup & Database)

### Task 1: Khởi tạo dự án (Project Initialization)
* **Mô tả:** Thiết lập môi trường và cấu hình kết nối.
* **Chi tiết thực hiện:**
    * Thêm các thư viện (Dependencies): Spring Web, Spring Data JPA, PostgreSQL Driver, Spring Security, Validation, JJWT, Lombok.
    * Cấu hình kết nối PostgreSQL và biến môi trường JWT (`jwt.secret`, `jwt.expiration`) trong file `application.properties`. Thời gian sống (Expiration) của token nên để khoảng 1-24 giờ để dễ test.
* **Commit:** `feat: setup project, database connection and jwt properties`

---

### Task 2: Tạo Thực thể (Entity) và Kho lưu trữ (Repository)
* **Mô tả:** Định nghĩa cấu trúc lưu trữ thông tin người dùng.
* **Chi tiết thực hiện:**
    * Tạo `User.java` (Entity) với các trường cơ bản: `id`, `email`, `password`, `fullName`, và `role` (ví dụ: chuỗi String lưu "ROLE_USER").
    * Cho `User` triển khai (implements) `UserDetails` của Spring Security.
    * Tạo `UserRepository.java` (Interface) kế thừa `JpaRepository`. Định nghĩa thêm phương thức `Optional<User> findByEmail(String email)` và `boolean existsByEmail(String email)`.
* **Commit:** `feat: create User entity with role and UserRepository`

---

## Phần 2: Luồng dữ liệu và Mã hóa (Data Flow & Encryption)

### Task 3: Đối tượng truyền dữ liệu (Data Transfer Object - DTO)
* **Mô tả:** Tạo các lớp để nhận dữ liệu đầu vào và định dạng dữ liệu trả về, tách biệt hoàn toàn với Entity.
* **Chi tiết thực hiện:**
    * Tạo `RegisterRequest.java`: Chứa `email`, `password`, `fullName`. Sử dụng `@NotBlank`, `@Email`, và `@Size` để xác thực (Validation).
    * Tạo `LoginRequest.java`: Chứa `email`, `password`.
    * Tạo `AuthResponse.java`: Trả về thông tin sau khi đăng nhập thành công, gồm `accessToken` và `tokenType` (Bearer).
    * Tạo `UserResponse.java`: Lớp DTO trả về thông tin người dùng (chỉ gồm `id`, `email`, `fullName`, `role` - tuyệt đối không trả về `password`).
* **Commit:** `feat: implement DTOs for auth flow`

---

### Task 4: Cấu hình Mã hóa mật khẩu (Password Hashing)
* **Mô tả:** Đảm bảo mật khẩu không bao giờ được lưu dưới dạng văn bản gốc (Plain-text).
* **Chi tiết thực hiện:**
    * Tạo lớp `SecurityConfig` (hoặc `AppConfig`) và khai báo một `@Bean` trả về `BCryptPasswordEncoder`.
* **Commit:** `feat: add BCrypt password encoder`

---

## Phần 3: Dịch vụ nghiệp vụ (Business Service) và JWT

### Task 5: Trình tạo và kiểm tra mã thông báo (JWT Provider)
* **Mô tả:** Thành phần chuyên trách xử lý chuỗi JWT.
* **Chi tiết thực hiện:**
    * Tạo lớp `JwtTokenProvider`.
    * Viết hàm `generateToken(Authentication authentication)` để tạo chuỗi JWT từ email của người dùng.
    * Viết hàm `getEmailFromJWT(String token)` để giải mã.
    * Viết hàm `validateToken(String token)` để kiểm tra tính hợp lệ và thời hạn của token.
* **Commit:** `feat: implement JwtTokenProvider for access token logic`

---

### Task 6: Dịch vụ Xác thực (Authentication Service)
* **Mô tả:** Nơi chứa logic nghiệp vụ cốt lõi cho Đăng ký và Đăng nhập.
* **Chi tiết thực hiện:**
    * Tạo `AuthService.java`.
    * **Logic Đăng ký (Register):** Kiểm tra email đã tồn tại chưa (dùng `existsByEmail`). Nếu có, ném lỗi (Exception). Nếu chưa, mã hóa mật khẩu bằng BCrypt, gán quyền mặc định (ROLE_USER), lưu vào DB và trả về thông báo thành công.
    * **Logic Đăng nhập (Login):** Nhận `email` và `password`. Dùng `AuthenticationManager` của Spring Security để xác thực. Nếu thành công, gọi `JwtTokenProvider` để tạo Access Token và trả về `AuthResponse`.
* **Commit:** `feat: implement logic for register and login in AuthService`

---

## Phần 4: Bảo mật (Spring Security Configuration)

### Task 7: Chi tiết người dùng (UserDetailsService)
* **Mô tả:** Kết nối cơ sở dữ liệu với hệ thống của Spring Security.
* **Chi tiết thực hiện:**
    * Tạo `CustomUserDetailsService` implements `UserDetailsService`.
    * Ghi đè (Override) hàm `loadUserByUsername(String email)`. Tìm user bằng email, nếu không thấy ném lỗi `UsernameNotFoundException`.
* **Commit:** `feat: implement CustomUserDetailsService`

---

### Task 8: Bộ lọc Xác thực JWT (JWT Authentication Filter)
* **Mô tả:** Đánh chặn mọi yêu cầu (Request) để kiểm tra xem người dùng có gửi kèm Token hợp lệ không.
* **Chi tiết thực hiện:**
    * Tạo `JwtAuthenticationFilter` kế thừa `OncePerRequestFilter`.
    * Lấy chuỗi JWT từ tiêu đề (Header) `Authorization`.
    * Nếu token hợp lệ, lấy thông tin User từ cơ sở dữ liệu, tạo đối tượng ngữ cảnh bảo mật (Security Context) để báo cho Spring biết user này đã đăng nhập.
* **Commit:** `feat: add JwtAuthenticationFilter`

---

### Task 9: Chuỗi bộ lọc bảo mật (Security Filter Chain)
* **Mô tả:** Thiết lập luật lệ (Rules) truy cập cho toàn hệ thống.
* **Chi tiết thực hiện:**
    * Trong `SecurityConfig`, cấu hình `SecurityFilterChain`.
    * Vô hiệu hóa CSRF và chuyển quản lý phiên (Session Management) sang dạng Không trạng thái (Stateless).
    * Phân quyền (Authorization): Cho phép truy cập công khai (Permit All) vào `/api/auth/register` và `/api/auth/login`. Yêu cầu xác thực (Authenticated) với mọi endpoint khác.
    * Đăng ký `JwtAuthenticationFilter` chạy trước bộ lọc mặc định của Spring.
* **Commit:** `feat: configure SecurityFilterChain and endpoint access rules`

---

## Phần 5: Điểm tiếp nhận API (Controller)

### Task 10: Trình điều khiển Xác thực (Auth Controller)
* **Mô tả:** Giao tiếp với frontend/client.
* **Chi tiết thực hiện:**
    * Tạo `AuthController.java` (`@RestController`, `@RequestMapping("/api/auth")`).
    * Tạo endpoint `POST /register`: Nhận `RegisterRequest` và gọi Service xử lý.
    * Tạo endpoint `POST /login`: Nhận `LoginRequest` và trả về Access Token.
    * Tạo endpoint `GET /me`: Đọc thông tin từ ngữ cảnh bảo mật (`SecurityContextHolder`), lấy User hiện tại và trả về dưới dạng `UserResponse` DTO.
* **Commit:** `feat: create AuthController with register, login, and me endpoints`

---

## Phần 6: Kiểm thử bằng Postman (Testing)

### Task 11: Kịch bản kiểm thử (Test Scenarios)
* **Mô tả:** Kiểm tra toàn bộ luồng chạy thực tế.
* **Chi tiết thực hiện:**
    * **Đăng ký (Register):** Gọi `POST /register` với email và password hợp lệ. Kỳ vọng mã HTTP 200 (hoặc 201). Kiểm tra trong Database (PostgreSQL) xem mật khẩu đã được băm chưa.
    * **Đăng ký lỗi:** Thử đăng ký lại với email vừa dùng. Kỳ vọng hệ thống báo lỗi.
    * **Đăng nhập (Login):** Gọi `POST /login` với tài khoản vừa tạo. Kỳ vọng nhận được `accessToken`.
    * **Lấy thông tin cá nhân:** Gọi `GET /me` (không gắn token). Kỳ vọng lỗi `401 Unauthorized` hoặc `403 Forbidden`.
    * **Gắn Token và gọi lại:** Cấu hình Postman (Auth tab -> Bearer Token), dán token vào và gọi lại `GET /me`. Kỳ vọng trả về JSON thông tin người dùng (không chứa mật khẩu).