# BookNest - Premium Online Book Store

A production-quality full-stack e-commerce application built with Java Spring Boot, featuring a premium UI design inspired by modern bookstore websites.

## 🚀 Tech Stack

### Backend
- **Java 25** - Modern Java with latest features
- **Spring Boot 3.2.0** - Framework for building production-ready applications
- **Spring MVC** - Web framework for REST APIs
- **Spring Security** - Authentication and authorization with BCrypt
- **Spring Data JPA** - Database abstraction layer
- **Hibernate** - ORM for database operations
- **Maven** - Dependency management and build tool

### Database
- **MySQL** - Relational database for data persistence

### Frontend
- **Thymeleaf** - Server-side template engine
- **Bootstrap 5** - Responsive UI framework
- **HTML5/CSS3** - Modern web standards
- **JavaScript** - Frontend interactions and animations

### Libraries & Tools
- **GSAP** - Professional-grade animation library
- **AOS** - Scroll reveal animations
- **Swiper.js** - Touch slider library
- **Font Awesome** - Icon library
- **Chart.js** - Data visualization
- **Lombok** - Reduce boilerplate code
- **iTextPDF** - PDF generation for invoices

## ✨ Features

### User Features
- **Authentication** - User registration, login with role-based access
- **Book Browsing** - Search, filter, and sort books
- **Categories** - Browse books by category
- **Book Details** - View detailed information, reviews, and ratings
- **Shopping Cart** - Add, update, and remove items
- **Wishlist** - Save favorite books for later
- **Checkout** - Multi-step checkout process
- **Order Management** - View order history and status
- **Profile Management** - Update personal information
- **Address Management** - Multiple shipping addresses
- **Reviews** - Write and read book reviews

### Admin Features
- **Dashboard** - Overview with statistics and metrics
- **Book Management** - CRUD operations for books
- **Category Management** - Manage book categories
- **Order Management** - View and update order status
- **Review Moderation** - Approve user reviews
- **Inventory Alerts** - Low stock notifications
- **Analytics** - Revenue, orders, and user statistics

### Design Features
- **Premium UI** - Modern, elegant design with warm color palette
- **Responsive Design** - Works on desktop, tablet, and mobile
- **Smooth Animations** - GSAP, AOS, and Swiper.js animations
- **Interactive Elements** - Hover effects, transitions, and micro-interactions
- **Accessibility** - Semantic HTML and ARIA labels
- **Performance** - Optimized loading and rendering

## 🎨 Design Specifications

### Color Palette
- **Primary**: #6F4E37 (Warm Brown)
- **Secondary**: #F8F5F2 (Cream)
- **Accent**: #C89B3C (Gold)
- **Text**: #222 (Dark Gray)
- **Background**: White cards on cream background

### Typography
- **Headings**: Playfair Display (Serif)
- **Body**: Poppins (Sans-serif)

### UI Elements
- **Buttons**: Rounded corners with smooth hover effects
- **Cards**: White with soft shadows and hover lift effect
- **Animations**: Smooth, premium feel with scroll reveals

## 📁 Project Structure

```
BookNest/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── booknest/
│   │   │           ├── BookNestApplication.java
│   │   │           ├── config/
│   │   │           │   ├── SecurityConfig.java
│   │   │           │   └── DataInitializer.java
│   │   │           ├── controller/
│   │   │           │   ├── AdminController.java
│   │   │           │   ├── AuthController.java
│   │   │           │   ├── BookController.java
│   │   │           │   ├── CartController.java
│   │   │           │   ├── CategoryController.java
│   │   │           │   ├── CheckoutController.java
│   │   │           │   ├── HomeController.java
│   │   │           │   ├── UserController.java
│   │   │           │   └── WishlistController.java
│   │   │           ├── dto/
│   │   │           ├── entity/
│   │   │           │   ├── Address.java
│   │   │           │   ├── Author.java
│   │   │           │   ├── Book.java
│   │   │           │   ├── Cart.java
│   │   │           │   ├── CartItem.java
│   │   │           │   ├── Category.java
│   │   │           │   ├── Order.java
│   │   │           │   ├── OrderItem.java
│   │   │           │   ├── Payment.java
│   │   │           │   ├── Review.java
│   │   │           │   ├── Role.java
│   │   │           │   ├── User.java
│   │   │           │   └── Wishlist.java
│   │   │           ├── repository/
│   │   │           ├── service/
│   │   │           └── util/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   │           ├── admin/
│   │           ├── fragments/
│   │           ├── about.html
│   │           ├── book-details.html
│   │           ├── books.html
│   │           ├── cart.html
│   │           ├── categories.html
│   │           ├── category-books.html
│   │           ├── checkout.html
│   │           ├── contact.html
│   │           ├── home.html
│   │           ├── login.html
│   │           ├── order-success.html
│   │           ├── profile.html
│   │           ├── register.html
│   │           └── wishlist.html
└── pom.xml
```

## 🛠️ Setup Instructions

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Database Setup
1. Create a MySQL database named `booknest`
2. Update database credentials in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/booknest
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run with Maven:
```bash
mvn spring-boot:run
```
4. Access the application at `http://localhost:8080`

### Default Admin Credentials
- **Email**: admin@booknest.com
- **Password**: admin123

## 📚 Sample Data

The application automatically initializes with:
- **10 Categories**: Fiction, Business, Technology, Programming, Self Help, History, Romance, Children, Comics, Science
- **10 Authors**: Including James Clear, Robert Kiyosaki, Paulo Coelho, J.K. Rowling, etc.
- **60+ Books**: With realistic data, prices in INR, discounts, and ratings
- **1 Admin User**: For administrative access

## 🔐 Security Features

- **BCrypt Password Encoding** - Secure password storage
- **Role-Based Access Control** - ROLE_ADMIN and ROLE_USER
- **Session Management** - Secure session handling
- **CSRF Protection** - Cross-site request forgery protection
- **SQL Injection Prevention** - Parameterized queries via JPA

## 🌐 API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /books` - Browse all books
- `GET /book/{id}` - View book details
- `GET /categories` - Browse categories
- `GET /category/{id}` - Books by category
- `GET /login` - Login page
- `GET /register` - Registration page

### User Endpoints (Requires Authentication)
- `GET /profile` - User profile
- `GET /cart` - Shopping cart
- `POST /cart/add` - Add to cart
- `GET /wishlist` - User wishlist
- `GET /checkout` - Checkout page
- `POST /checkout/place-order` - Place order

### Admin Endpoints (Requires ROLE_ADMIN)
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/books` - Manage books
- `GET /admin/categories` - Manage categories
- `GET /admin/orders` - Manage orders
- `GET /admin/reviews` - Moderate reviews

## 🎯 Key Features Implementation

### MVC Architecture
- **Controllers**: Handle HTTP requests and responses
- **Services**: Business logic layer
- **Repositories**: Data access layer
- **Entities**: Database models
- **DTOs**: Data transfer objects

### Database Design
- **Normalized Schema** - Proper relationships and constraints
- **Soft Delete** - Logical deletion for data integrity
- **Audit Fields** - Created/updated timestamps
- **Indexes** - Optimized queries

### Frontend Features
- **Thymeleaf Fragments** - Reusable components (header, footer)
- **Bootstrap 5** - Responsive grid system and components
- **Custom CSS** - Premium styling with CSS variables
- **JavaScript** - Interactive features and animations

## 📝 Notes

- Images are sourced from Unsplash for demonstration
- PDF invoice generation uses iText library
- All prices are in Indian Rupees (₹)
- The application uses Hibernate's DDL auto-update for schema creation
- Data initialization runs on application startup

## 🚀 Deployment

### Build for Production
```bash
mvn clean package
```

### Run JAR File
```bash
java -jar target/booknest-1.0.0.jar
```

### Environment Variables
Set the following environment variables for production:
- `DB_URL` - Database connection URL
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password

## 🤝 Contributing

This is a final-year engineering project demonstrating full-stack development skills with Java Spring Boot.

## 📄 License

This project is created for educational purposes.

## 👨‍💻 Author

Built as a premium full-stack e-commerce application suitable for professional portfolio and academic projects.

---

**BookNest** - Your Premium Online Book Store 📚✨
