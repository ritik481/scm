# SCM - Smart Contact Manager

A Spring Boot web application for managing contacts on the cloud. This project allows users to register, login, and manage their personal contacts with features like social links, favorites, and more.

## Features

- User registration and authentication
- Contact management (add, edit, delete contacts)
- Social media links integration
- Favorite contacts
- Responsive UI with Tailwind CSS
- H2 in-memory database for development
- MySQL support for production
- Thymeleaf templating engine
- Spring Security integration (planned)

## Tech Stack

- **Backend:** Spring Boot 3.5.6, Java 21
- **Frontend:** Thymeleaf, Tailwind CSS, JavaScript
- **Database:** H2 (development), MySQL (production)
- **Build Tool:** Maven
- **ORM:** Spring Data JPA with Hibernate

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- Node.js and npm (for Tailwind CSS compilation)

## Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd scm
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Install frontend dependencies:**
   ```bash
   npm install
   ```

4. **Compile Tailwind CSS:**
   ```bash
   npm run build-css
   ```

## Running the Application

1. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```
   Or using the Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Access the application:**
   - Open your browser and go to `http://localhost:8082`
   - H2 Database Console: `http://localhost:8082/h2-console`
     - JDBC URL: `jdbc:h2:mem:scm_db`
     - Username: `sa`
     - Password: (leave blank)

## Usage

### Pages Available:
- **Home:** `/home` - Main landing page
- **About:** `/about` - Information about the application
- **Services:** `/services` - Services offered
- **Contact:** `/contact` - Contact information
- **Login:** `/login` - User login page
- **Signup:** `/signup` - User registration page

### User Registration:
1. Navigate to `/signup`
2. Fill in the registration form
3. Submit to create a new account

### Database Configuration

The application uses H2 in-memory database by default. For production, update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/scm_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## Project Structure

```
src/
├── main/
│   ├── java/com/scm/scm/
│   │   ├── controllers/          # Web controllers
│   │   ├── entities/             # JPA entities
│   │   ├── forms/                # Form DTOs
│   │   ├── helpers/              # Utility classes
│   │   ├── repositories/         # Data repositories
│   │   ├── services/             # Business logic
│   │   └── ScmApplication.java   # Main application class
│   └── resources/
│       ├── static/               # CSS, JS, images
│       ├── templates/            # Thymeleaf templates
│       └── application.properties # Configuration
└── test/                         # Test classes
```

## API Endpoints

- `GET /home` - Home page
- `GET /about` - About page
- `GET /services` - Services page
- `GET /contact` - Contact page
- `GET /login` - Login page
- `GET /signup` - Signup page
- `POST /do-register` - Process user registration
- `POST /set-theme` - Set user theme preference

## Development

### Building for Production
```bash
mvn clean package
```

### Running Tests
```bash
mvn test
```

### Hot Reload
The application uses Spring DevTools for hot reloading during development.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Boot for the framework
- Thymeleaf for templating
- Tailwind CSS for styling
- H2 Database for development
