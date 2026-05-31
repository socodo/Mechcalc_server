# MechCalc - Mechanical Calculator Application

## Giới thiệu

MechCalc là ứng dụng REST API được xây dựng trên Spring Boot. Codebase hiện dùng cấu trúc theo feature/module, mỗi module tự chứa controller, service, DTO, entity, repository và mapper liên quan.

## Yêu cầu hệ thống

| Stack       | Phiên bản | Ghi chú         |
| ----------- | --------- | --------------- |
| Java        | 21+       | LTS version     |
| Spring Boot | 4.0.3     | Latest          |
| PostgreSQL  | 15+       | Database        |
| Maven       | 3.9+      | Build tool      |
| Git         | 2.40+     | Version control |

## Cấu trúc dự án

```
mechcalc/
├── src/
│   ├── main/
│   │   ├── java/com/socodo/mechcalc/
│   │   │   ├── auth/             # Authentication, JWT, Google login
│   │   │   ├── user/             # User profile and admin user management
│   │   │   ├── project/          # Project APIs and sync
│   │   │   ├── motor/            # Motor catalog and calculation APIs
│   │   │   ├── common/           # Shared DTOs and reusable API contracts
│   │   │   ├── config/           # Security and application configuration
│   │   │   └── exception/        # Error codes and global exception handling
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/socodo/mechcalc/
│       │   └── MechcalcApplicationTests.java
│       └── resources/
│           └── application-test.properties
├── .env.example                  # Environment variables template
├── .gitignore
├── pom.xml
└── README.md
```

## Quy ước cấu trúc module

Mỗi feature như `auth`, `user`, `project`, `motor` nên giữ cấu trúc nhất quán:

| Package          | Vai trò                                                        |
| ---------------- | -------------------------------------------------------------- |
| `controller`     | Nhận HTTP request, validate input, trả `ApiResponse`           |
| `service`        | Chứa business logic và transaction boundary                    |
| `repository`     | Truy cập database bằng Spring Data JPA                         |
| `entity`         | JPA entity, không expose trực tiếp ra API                      |
| `dto/request`    | Request DTO và validation annotation                           |
| `dto/response`   | Response DTO trả cho client                                    |
| `mapper`         | MapStruct mapper chuyển đổi giữa entity và DTO                 |

## Cài đặt và chạy

### 1. Clone repository

```bash
git clone <repository-url>
cd mechcalc
```

### 2. Cấu hình môi trường

```bash
# Copy file environment mẫu
cp .env.example .env

# Chỉnh sửa các biến môi trường
nano .env
```

Nội dung file `.env`:

```properties
SERVER_PORT=8080

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mechcalc
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver

SECURITY_JWT_SECRET_KEY=change-this-secret-key-to-at-least-32-bytes
SECURITY_JWT_EXPIRATION_TIME=3600000
SECURITY_JWT_REFRESH_TOKEN_EXPIRATION=604800000

GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

### 3. Tạo database

```sql
CREATE DATABASE mechcalc;
```

### 4. Chạy ứng dụng

```bash
# Development
./mvnw spring-boot:run

# Run tests
./mvnw test

# Hoặc build và chạy JAR
./mvnw clean package -DskipTests
java -jar target/mechcalc-0.0.1-SNAPSHOT.jar
```

### 5. Truy cập

- **API Base URL:** http://localhost:8080/api/v1
- **Swagger UI:** http://localhost:8080/api/v1/swagger-ui.html
- **API Docs:** http://localhost:8080/api/v1/v3/api-docs

## Git Flow

### Branch naming convention

| Branch      | Mô tả                                      | Ví dụ                         |
| ----------- | ------------------------------------------ | ----------------------------- |
| `main`      | Production code, luôn stable               | -                             |
| `develop`   | Development branch, merge features vào đây | -                             |
| `feature/*` | Tính năng mới                              | `feature/user-authentication` |
| `bugfix/*`  | Sửa bug trên develop                       | `bugfix/login-validation`     |
| `hotfix/*`  | Sửa bug khẩn cấp trên production           | `hotfix/security-patch`       |
| `release/*` | Chuẩn bị release                           | `release/v1.0.0`              |

### Workflow

```
main ─────────────────────────────────────────────► (production)                                  ▲
  │                                           │
  │                                           │ merge to
  ▼                                           │   final
develop ──────┬──────┬──────┬────────────────────────►
              │      │      │              ▲
              ▼      ▼      ▼              │ merge to dev
         feature  feature  bugfix ─────────┘
```

### Quy trình làm việc

#### 1. Bắt đầu feature mới

```bash
# Cập nhật develop mới nhất
git checkout develop
git pull origin develop

# Tạo branch feature
git checkout -b feature/ten-tinh-nang

# Code và commit
git add .
git commit -m "feat: mô tả tính năng"

# Push lên remote
git push origin feature/ten-tinh-nang
```

#### 2. Tạo Pull Request

- Tạo PR từ `feature/*` vào `develop`
- Yêu cầu ít nhất 1 reviewer approve
- Chạy CI/CD tests pass
- Squash merge khi hoàn thành

#### 3. Hotfix quy trình

```bash
# Tạo hotfix từ main
git checkout main
git pull origin main
git checkout -b hotfix/ten-loi

# Fix và commit
git commit -m "fix: mô tả fix"

# Merge vào cả main và develop
git checkout main
git merge hotfix/ten-loi
git checkout develop
git merge hotfix/ten-loi
```

### Commit message convention

Sử dụng [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

| Type       | Mô tả                             |
| ---------- | --------------------------------- |
| `feat`     | Tính năng mới                     |
| `fix`      | Sửa bug                           |
| `docs`     | Thay đổi documentation            |
| `style`    | Format code, không thay đổi logic |
| `refactor` | Refactor code                     |
| `test`     | Thêm/sửa tests                    |
| `chore`    | Cập nhật build, dependencies      |

**Ví dụ:**

```bash
git commit -m "feat(auth): add JWT authentication"
git commit -m "fix(user): resolve null pointer in getUserById"
git commit -m "docs: update README with git flow"
```

## Code conventions

### Java

- Sử dụng **camelCase** cho variables và methods
- Sử dụng **PascalCase** cho classes
- Sử dụng **SCREAMING_SNAKE_CASE** cho constants
- Mỗi class một file
- Lombok cho boilerplate code

### API Design

- RESTful conventions
- Sử dụng HTTP methods đúng mục đích (GET, POST, PUT, DELETE)
- Response format thống nhất với `ApiResponse<T>`
- Pagination với `PageResponse<T>`

### Package structure

```
controller/
├── UserController.java        # /api/users

dto/
├── request/
│   └── CreateUserRequest.java
└── response/
    └── UserResponse.java

entity/
└── User.java

repository/
└── UserRepository.java

service/
├── UserService.java           # Interface
└── impl/
    └── UserServiceImpl.java   # Implementation

mapper/
└── UserMapper.java            # MapStruct interface
```

## Testing

```bash
# Chạy tất cả tests
./mvnw test

# Chạy test với coverage report
./mvnw test jacoco:report

# Chạy integration tests
./mvnw verify -P integration-test
```

## Dependencies chính

| Dependency                       | Mục đích             |
| -------------------------------- | -------------------- |
| `spring-boot-starter-web`        | REST API             |
| `spring-boot-starter-data-jpa`   | Database ORM         |
| `spring-boot-starter-validation` | Input validation     |
| `postgresql`                     | PostgreSQL driver    |
| `lombok`                         | Reduce boilerplate   |
| `mapstruct`                      | Object mapping       |
| `springdoc-openapi`              | Swagger/OpenAPI docs |
| `nimbus-jose-jwt`                | JWT handling         |

## Liên hệ

- **Team:** Socodo
- **Email:** anhtranym18@gmail.com

## License

MIT License
