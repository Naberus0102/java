# Quy Trình Phát Triển Chức Năng Đăng Nhập Cơ Bản — Bản Rút Gọn Cho Người Mới Bắt Đầu
(Spring Boot + PostgreSQL + BCrypt + JWT — chưa dùng Spring Security)

> **Đối tượng phù hợp:** Sinh viên năm 1–2 đã học Java OOP, mới bắt đầu Spring Boot (biết `@RestController`, `@Service`, `@Repository`, Dependency Injection cơ bản), lần đầu làm tính năng đăng nhập.
>
> **Vì sao có bản này:** So sánh `idea.md` (tích hợp Spring Security đầy đủ) và `idea_chatgpt.md` (bản rút gọn), hướng rút gọn — tự tay viết logic xác thực thay vì để Spring Security tự động hóa — phù hợp hơn cho người mới. Tài liệu này giữ hướng đi đó, đồng thời sửa vài chỗ dễ gây lỗi và bổ sung thêm ghi chú thực tế.

---

## Phần 1: Khởi tạo dự án & Cấu hình

### Task 1: Khởi tạo dự án và cấu hình kết nối
**Mô tả:** Thiết lập project với đúng thư viện cần thiết — không thừa, không thiếu.

**Chi tiết thực hiện:**
* Cài đặt PostgreSQL (local hoặc qua Docker), tạo sẵn một database rỗng, ví dụ `auth_demo`.
* Trên Spring Initializr, chọn: `Spring Web`, `Spring Data JPA`, `PostgreSQL Driver`, `Validation`, `Lombok`.
* Thêm thủ công vào `pom.xml` (không có sẵn dạng checkbox trên Initializr):
    * `jjwt-api`, `jjwt-impl`, `jjwt-jackson` — thư viện JJWT để tạo và đọc JWT.
    * `spring-security-crypto` — **chỉ** để dùng lớp `BCryptPasswordEncoder`.
* **Lưu ý quan trọng:** KHÔNG chọn checkbox "Spring Security" trên Initializr (tức không thêm `spring-boot-starter-security`). Nếu thêm, Spring Boot sẽ tự động yêu cầu đăng nhập (Basic Auth) ở MỌI endpoint ngay khi chạy — dẫn đến `401`/`403` ở khắp nơi dù chưa viết dòng bảo mật nào, rất dễ gây bối rối. Bản rút gọn này chỉ cần `spring-security-crypto` (thư viện nhẹ, không tự khóa API).
* Cấu hình `application.properties`: thông tin kết nối PostgreSQL, và 2 thuộc tính tự đặt `jwt.secret` (chuỗi bí mật, nên ≥ 32 ký tự vì JJWT yêu cầu khóa đủ mạnh cho thuật toán HS256, nếu ngắn quá sẽ ném lỗi khi chạy) và `jwt.expiration` (nên để khoảng 86400000 ms ≈ 1 ngày cho dễ test).

**Commit:** `feat: setup project, database connection and jwt config`

---

### Task 2: Entity và Repository
**Mô tả:** Định nghĩa cấu trúc lưu người dùng, giữ đơn giản.

**Chi tiết thực hiện:**
* Tạo `User.java` — Entity JPA thông thường (**không** implement `UserDetails`), gồm: `id` (`Long`, tự tăng), `email` (unique, not null), `password`, `fullName`.
* Tạo `UserRepository` kế thừa `JpaRepository<User, Long>`, thêm `Optional<User> findByEmail(String email)` và `boolean existsByEmail(String email)`.

**Lưu ý:** Đây là khác biệt lớn nhất so với cách làm chuẩn của Spring Security — Entity không cần override `getAuthorities()`, `isAccountNonExpired()`, v.v. Nhờ vậy dễ đọc hơn rất nhiều cho người mới.

**Commit:** `feat: create user entity and repository`

---

## Phần 2: DTO (Data Transfer Object)

### Task 3: Tạo các DTO
**Mô tả:** Tách dữ liệu vào/ra khỏi Entity — nguyên tắc nên tập từ đầu.

**Chi tiết thực hiện:**
* `RegisterRequest`: `email` (`@NotBlank`, `@Email`), `password` (`@NotBlank`, `@Size(min = 6)`), `fullName` (`@NotBlank`).
* `LoginRequest`: `email`, `password` (đều `@NotBlank`).
* `AuthResponse`: `accessToken`, `tokenType` (mặc định `"Bearer"`).
* `UserResponse`: `id`, `email`, `fullName` — **tuyệt đối không có `password`**.

**Lưu ý:** Nếu dùng Spring Boot 3.x, annotation validation nằm ở package `jakarta.validation.constraints` (không phải `javax.validation` như bản Spring Boot 2.x cũ).

**Commit:** `feat: implement DTOs for auth flow`

---

## Phần 3: Mã hóa mật khẩu

### Task 4: Cấu hình Password Encoder
**Mô tả:** Không bao giờ lưu mật khẩu dạng văn bản gốc.

**Chi tiết thực hiện:**
* Tạo class `AppConfig`, khai báo `@Bean` trả về `BCryptPasswordEncoder`.

**Lưu ý:** BCrypt tự sinh "salt" ngẫu nhiên và băm một chiều (không thể giải mã ngược) — dù cơ sở dữ liệu bị lộ, mật khẩu gốc vẫn an toàn.

**Commit:** `feat: configure BCrypt password encoder`

---

## Phần 4: JWT Provider

### Task 5: Trình tạo và kiểm tra token
**Mô tả:** Thành phần chuyên trách tạo và kiểm tra chuỗi JWT.

**Chi tiết thực hiện:**
* Tạo `JwtTokenProvider` với 3 hàm:
    * `generateToken(String email)`: tạo JWT với `email` làm subject, thời hạn lấy từ `jwt.expiration`, ký bằng `jwt.secret`.
    * `getEmailFromToken(String token)`: giải mã token, lấy ra `email`.
    * `validateToken(String token)`: bọc trong `try/catch`, bắt các lỗi `ExpiredJwtException`, `MalformedJwtException`, `SignatureException`..., trả về `true`/`false`.

**Lưu ý:** JWT gồm 3 phần `Header.Payload.Signature`. Server không cần lưu token ở đâu cả (stateless) — mỗi request tự "mang theo" bằng chứng đăng nhập trong header của nó.

**Commit:** `feat: implement JwtTokenProvider`

---

## Phần 5: Business Service

### Task 6: AuthService — Logic đăng ký & đăng nhập
**Mô tả:** Nơi chứa logic nghiệp vụ, chưa dùng `AuthenticationManager`.

**Chi tiết thực hiện:**
* **Đăng ký:** `existsByEmail()` → nếu có, ném lỗi (ví dụ `EmailAlreadyExistsException`); nếu chưa, mã hóa mật khẩu bằng `passwordEncoder.encode()`, lưu `User`.
* **Đăng nhập:** `findByEmail()` → nếu không thấy, ném lỗi; nếu thấy, so sánh bằng `passwordEncoder.matches(rawPassword, user.getPassword())` (**không** dùng `==` hay `.equals()` vì một bên đã băm). Nếu khớp, gọi `JwtTokenProvider.generateToken()`, trả `AuthResponse`; nếu không khớp, ném lỗi.

**Lưu ý:** Nên dùng chung một thông báo lỗi (ví dụ: "Email hoặc mật khẩu không đúng") cho cả hai trường hợp "không tìm thấy email" và "sai mật khẩu", tránh để lộ email nào đã tồn tại trong hệ thống — thói quen bảo mật tốt nên tập từ đầu.

**Commit:** `feat: implement AuthService (register and login)`

---

## Phần 6: Controller

### Task 7: AuthController
**Mô tả:** Giao tiếp với client — kiểm tra token thủ công thay cho `JwtAuthenticationFilter`.

**Chi tiết thực hiện:**
* `AuthController` (`@RestController`, `@RequestMapping("/api/auth")`).
* `POST /register`: nhận `@Valid RegisterRequest`, gọi service, trả `201 Created`.
* `POST /login`: nhận `@Valid LoginRequest`, gọi service, trả `200 OK` + `AuthResponse`.
* `GET /me`: đọc header `Authorization`, tách tiền tố `"Bearer "`; thiếu hoặc token không hợp lệ → ném lỗi (`InvalidTokenException`); hợp lệ → `getEmailFromToken()` → tìm `User` → trả `UserResponse`.

**Lưu ý:** Vì chỉ có một endpoint cần bảo vệ, kiểm tra token ngay trong Controller là hợp lý cho dự án đầu tiên. Nếu sau này có thêm nhiều endpoint cần đăng nhập, hãy gom đoạn kiểm tra này thành một hàm dùng chung, hoặc nâng cấp lên `JwtAuthenticationFilter` thật sự (xem phần mở rộng bên dưới).

**Commit:** `feat: create AuthController with register, login and me endpoints`

---

## Phần 7: Xử lý ngoại lệ

### Task 8: Global Exception Handler
**Mô tả:** Trả lỗi JSON thống nhất thay vì để lộ stack trace mặc định.

**Chi tiết thực hiện:**
* Tạo class dùng `@RestControllerAdvice`.
* Bắt lỗi tự định nghĩa: `EmailAlreadyExistsException` → `409 Conflict`; `InvalidCredentialsException` → `401 Unauthorized`; `InvalidTokenException` → `401 Unauthorized`.
* Bắt lỗi validation `MethodArgumentNotValidException` → `400 Bad Request`, kèm danh sách field bị lỗi.
* Trả JSON thống nhất, ví dụ: `{ "timestamp": ..., "status": ..., "message": ... }`.

**Commit:** `feat: add global exception handler`

---

## Phần 8: Kiểm thử bằng Postman

### Task 9: Kịch bản kiểm thử
**Mô tả:** Kiểm tra toàn bộ luồng chạy thực tế, đối chiếu với kỳ vọng cụ thể.

**Chi tiết thực hiện:**
1. `POST /register` với dữ liệu hợp lệ → kỳ vọng `201 Created`. Mở PostgreSQL, kiểm tra cột `password` là chuỗi băm (bắt đầu bằng `$2a$`/`$2b$`), không phải văn bản gốc.
2. `POST /register` lại với email vừa dùng → kỳ vọng `409 Conflict`.
3. `POST /register` thiếu field hoặc sai định dạng email → kỳ vọng `400 Bad Request` kèm chi tiết lỗi.
4. `POST /login` với tài khoản vừa tạo → kỳ vọng `200 OK` kèm `accessToken`.
5. `POST /login` sai mật khẩu → kỳ vọng `401 Unauthorized`.
6. `GET /me` không gắn token → kỳ vọng `401 Unauthorized`.
7. `GET /me` với token sai hoặc hết hạn → kỳ vọng `401 Unauthorized`.
8. `GET /me` với token hợp lệ (Postman → tab Authorization → Bearer Token) → kỳ vọng `200 OK` kèm `UserResponse` (không chứa `password`).

---

## Lỗi thường gặp cần lưu ý

* Thấy `401`/`403` ở mọi API dù chưa viết bảo mật → do lỡ thêm `spring-boot-starter-security` đầy đủ thay vì chỉ `spring-security-crypto` (xem lại Task 1).
* Quên khoảng trắng sau `"Bearer"` khi tách chuỗi header, hoặc quên tiền tố này khi test trên Postman.
* Quên `@Valid` trước tham số DTO trong Controller → validation không chạy dù DTO đã gắn annotation.
* So sánh mật khẩu bằng `==` hoặc `.equals()` thay vì `passwordEncoder.matches()` → luôn sai vì một bên là chuỗi thô, một bên đã băm.
* Trả nhầm `User` (Entity) thay vì `UserResponse` (DTO) ở response → lộ trường `password`.

---

## Phần mở rộng (không bắt buộc)

Sau khi chạy trơn tru bản rút gọn này và hiểu rõ luồng thủ công, có thể nâng cấp dần lên:

* Spring Security thật sự: `UserDetails`, `UserDetailsService`, `AuthenticationManager`, `JwtAuthenticationFilter`, `SecurityFilterChain`.
* Phân quyền (Role/Authorization): `ROLE_USER`, `ROLE_ADMIN`.
* Refresh Token, đăng xuất (blacklist token), xác thực email, giới hạn số lần đăng nhập sai (rate limiting).

Hiểu rõ "bản thủ công" trước sẽ giúp việc học Spring Security sau này dễ dàng hơn nhiều — vì sẽ biết chính xác framework đang tự động hóa bước nào thay cho mình.
