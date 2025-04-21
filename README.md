## 1. Reading Notes - Reading Notes Management System
A Spring Boot-based web application for creating, managing, and sharing reading notes. Supports user registration/login, CRUD operations for notes, and admin controls.

## 2. Project Description
**Core Features**  
- User authentication (Registration/Login/Permission Management)
- Create/Edit/Delete reading notes
- Filter notes by title/author/date range
- Admin user management

**Target Users**  
- Student community needing reading notes management
- Book enthusiasts
- Academic researchers

**Project Status**  
`Under Development` | Latest Version: v2.5.0

## 3. Installation Guide
**Prerequisites**  
- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Latest Chrome/Firefox browsers

**Installation Steps**  
```bash
git clone https://github.com/CPT202-2025-G22/ReadingNotes.git
cd ReadingNotes
mvn clean install
```

**Database Setup**  
1. Create MySQL database:
```sql
CREATE DATABASE reading_notes DEFAULT CHARSET utf8mb4;
```
2. Configure database connection in `src/main/resources/application.properties`

## 4. Usage
### 4.1 Basic Operations
1. **Start development server**  
```bash
mvn spring-boot:run
```

2. **Access application**  
Visit http://localhost:8080

3. **Account Usage**  
- Admin: admin / admin123  
  - Access admin panel at /admin
- Regular user: user / user123  
  - Create/manage personal notes

### 4.2 Feature Examples
**Create New Note**  
1. Login and click "My Logs" in navigation
2. Click "New Log" button
3. Fill form and save

**Filter Notes**  
1. Click filter icon in notes list
2. Set filter criteria (date range/author/reading time)
3. Click "Filter" to view results

### 4.3 Configuration
**Environment Variables**  
| Variable  | Example Value              | Required | Description          |
|-----------|---------------------------|----------|----------------------|
| SERVER_PORT| 8080                      | Optional | Service port        |
| DB_URL     | jdbc:mysql://localhost:3306/reading_notes | Yes | Database URL      |
| DB_USER    | root                      | Yes     | Database username   |
| DB_PWD     | your_secure_password      | Yes     | Database password   |

**Production Deployment**  
```bash
mvn clean package
java -jar target/reading-notes-1.0.0.jar
```

## 5. Project Structure
```text
c:\Users\Mahr.LAPTOP-0SB35923\Desktop\ReadingNotes/
├── src/
│   ├── main/
│   │   ├── java/cn/edu/xjtlu/readingnotes/
│   │   │   ├── config/                # Spring configuration classes
│   │   │   │   ├── EmailConfig.java
│   │   │   │   └── ThymeleafConfig.java
│   │   │   ├── controller/            # MVC controllers
│   │   │   │   ├── readinglog/
│   │   │   │   │   └── ReadingLogController.java
│   │   │   │   └── user/
│   │   │   │       ├── AdminController.java
│   │   │   │       └── UserViewController.java
│   │   │   ├── model/                 # Data entities
│   │   │   │   └── entity/
│   │   │   │       └── ReadingLog.java
│   │   │   ├── repository/            # Data access layer
│   │   │   │   ├── readinglog/
│   │   │   │   │   ├── ReadingLogRepo.java
│   │   │   │   │   └── ReadingLog_.java
│   │   │   │   └── user/
│   │   │   │       └── UserRepo.java
│   │   │   ├── service/               # Business logic layer
│   │   │   │   ├── readinglog/
│   │   │   │   │   ├── ReadingLogService.java
│   │   │   │   │   └── ReadingLogSpecs.java
│   │   │   │   └── user/
│   │   │   │       └── UserInfoService.java
│   │   │   ├── storage/               # File storage handling
│   │   │   │   └── FileSystemStorageService.java
│   │   │   ├── util/                  # Utilities and enums
│   │   │   │   └── Role.java
│   │   │   └── ReadingnotesApplication.java # Main class
│   │   └── resources/
│   │       ├── static/                # Static resources
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       ├── templates/             # Thymeleaf templates
│   │       │   ├── fragments/
│   │       │   │   └── layout.html
│   │       │   ├── log-edit.html
│   │       │   ├── logs.html
│   │       │   ├── admin.html
│   │       │   └── profile.html
│   │       └── application.properties # Application config
│   └── test/
│       └── java/cn/edu/xjtlu/readingnotes/
│           ├── AcceptanceTestBase.java
│           ├── UserJourneyTest.java
│           ├── ApiContractTest.java
│           └── ReadingLogTest.java
├── pom.xml
└── README.md
```

## 6. Testing
**Run Tests**  
```bash
mvn test
```

**Test Coverage**  
- Unit test coverage: 85% 
- Integration test coverage: 75%
- Accepance test coverage: 95%

## 7. Contributing

Create Pull Request

**Coding Standards**  
- Follow Google Java Style Guide
- Write commit messages in English
- Adhere to OpenAPI specification for REST APIs

## 8. License
[MIT License](LICENSE)

## 9. Acknowledgements
- Special thanks to CPT202 course group for technical guidance
- Built using:
  - Spring Boot
  - Bootstrap 5
  - Thymeleaf

## 10. Contact
**Maintenance Team**  
- Email: cpt202-team@xjtlu.edu.cn  
- GitHub: [@CPT202-2025-G22](https://github.com/CPT202-2025-G22)