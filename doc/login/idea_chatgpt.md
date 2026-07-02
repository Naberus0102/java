# Quy Trình Phát Triển Chức Năng Đăng Nhập Cơ Bản
(Spring Boot + PostgreSQL + BCrypt + JWT Cơ Bản)

> Mục tiêu: Phù hợp với sinh viên năm 1–2 đã biết Java OOP và mới bắt đầu học Spring Boot.

## Phần 1. Khởi tạo dự án

### Task 1: Tạo project
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Validation
- Lombok
- JJWT

Cấu hình kết nối PostgreSQL và các thuộc tính JWT.

Commit:
`feat: initialize project`

---

## Phần 2. Thiết kế dữ liệu

### Task 2: Entity và Repository

Tạo `User` gồm:
- id
- email
- password
- fullName

Tạo `UserRepository`:
- `findByEmail(String email)`
- `existsByEmail(String email)`

Commit:
`feat: create user entity and repository`

---

## Phần 3. DTO

### Task 3

Tạo:
- RegisterRequest
- LoginRequest
- UserResponse
- AuthResponse

Sử dụng các Validation cơ bản:
- `@NotBlank`
- `@Email`

Commit:
`feat: create dto classes`

---

## Phần 4. Mã hóa mật khẩu

### Task 4

Khai báo `BCryptPasswordEncoder` dưới dạng Bean.

Commit:
`feat: configure password encoder`

---

## Phần 5. Business Service

### Task 5

Tạo `AuthService`.

Register:
- kiểm tra email tồn tại
- mã hóa mật khẩu
- lưu database

Login:
- tìm user theo email
- so sánh mật khẩu bằng BCrypt
- nếu đúng trả về thành công

Chưa sử dụng Spring Security.

Commit:
`feat: implement authentication service`

---

## Phần 6. JWT cơ bản

### Task 6

Tạo `JwtTokenProvider`.

Viết các hàm:
- generateToken(email)
- getEmailFromToken(token)
- validateToken(token)

Sau khi đăng nhập thành công:
- tạo JWT
- trả về token

Commit:
`feat: implement jwt provider`

---

## Phần 7. Controller

### Task 7

Tạo `AuthController`.

API:
- POST /register
- POST /login
- GET /me

Đối với `/me`:
- nhận JWT từ Header
- kiểm tra token
- lấy email
- tìm User
- trả về UserResponse

Chưa dùng Filter hoặc Spring Security.

Commit:
`feat: create authentication controller`

---

## Phần 8. Xử lý ngoại lệ

### Task 8

Tạo `@ControllerAdvice`.

Xử lý:
- Email đã tồn tại
- Sai tài khoản hoặc mật khẩu
- Token không hợp lệ

Trả về JSON thống nhất.

Commit:
`feat: add global exception handler`

---

## Phần 9. Kiểm thử

### Task 9

Kiểm tra:
1. Đăng ký thành công.
2. Đăng ký trùng email.
3. Đăng nhập đúng.
4. Đăng nhập sai mật khẩu.
5. Gọi `/me` không có token.
6. Gọi `/me` với token hợp lệ.

---

# Phần mở rộng (không bắt buộc)

Sau khi hoàn thành phiên bản trên có thể học tiếp:

- Spring Security
- UserDetails
- UserDetailsService
- AuthenticationManager
- JwtAuthenticationFilter
- SecurityFilterChain
- Role và Authorization
- Refresh Token

Các nội dung trên nên được xem là phiên bản nâng cao thay vì bắt buộc trong đồ án đầu tiên.
