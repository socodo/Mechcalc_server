# MechCalc - Mechanical Calculator Application

## Giới thiệu

MechCalc là ứng dụng REST API được xây dựng trên nền tảng Spring Boot, sử dụng kiến trúc phân tầng (Layered Architecture) theo best practices.

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
│   │   │   ├── config/           # Cấu hình ứng dụng (OpenAPI, Security, etc.)
│   │   │   ├── controller/       # REST Controllers - Xử lý HTTP request/response
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── request/      # Request DTOs (input validation)
│   │   │   │   └── response/     # Response DTOs (API responses)
│   │   │   ├── entity/           # JPA Entities - Ánh xạ database tables
│   │   │   ├── exception/        # Custom exceptions
│   │   │   │   └── handler/      # Global exception handlers
│   │   │   ├── mapper/           # MapStruct mappers (Entity <-> DTO)
│   │   │   ├── repository/       # Spring Data JPA Repositories
│   │   │   ├── service/          # Business logic interfaces
│   │   │   │   └── impl/         # Service implementations
│   │   │   └── util/             # Utility classes
│   │   │       └── constants/    # Application constants
│   │   └── resources/
│   │       ├── db/migration/     # Flyway migrations (nếu dùng)
│   │       ├── static/           # Static resources
│   │       ├── templates/        # Template files
│   │       └── application.properties
│   └── test/
│       └── java/com/socodo/mechcalc/
│           ├── controller/       # Controller unit tests
│           ├── service/          # Service unit tests
│           ├── repository/       # Repository tests
│           └── integration/      # Integration tests
├── .env.example                  # Environment variables template
├── .gitignore
├── pom.xml
└── README.md
```

## Mô tả các layer

| Layer          | Mô tả                                  | Quy tắc                                        |
| -------------- | -------------------------------------- | ---------------------------------------------- |
| **Controller** | Xử lý HTTP requests, validation input  | Không chứa business logic                      |
| **Service**    | Business logic, transaction management | Gọi Repository, không gọi trực tiếp Controller |
| **Repository** | Data access layer                      | Chỉ chứa queries, không có business logic      |
| **DTO**        | Transfer data giữa layers              | Request/Response riêng biệt                    |
| **Entity**     | Database mapping                       | Không expose trực tiếp ra API                  |
| **Mapper**     | Convert Entity <-> DTO                 | Sử dụng MapStruct                              |

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
DB_URL=jdbc:postgresql://localhost:5432/mechcalc
DB_USERNAME=postgres
DB_PASSWORD=your_password
SERVER_PORT=8080
```

### 3. Tạo database

```sql
CREATE DATABASE mechcalc;
```

### 4. Chạy ứng dụng

```bash
# Development
./mvnw spring-boot:run

# Hoặc build và chạy JAR
./mvnw clean package -DskipTests
java -jar target/mechcalc-0.0.1-SNAPSHOT.jar
```

### 5. Truy cập

- **API Base URL:** http://localhost:8080
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/v3/api-docs

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
