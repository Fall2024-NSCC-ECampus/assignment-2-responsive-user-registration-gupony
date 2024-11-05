***Responsive Login System***
This project builds on my previous responsive registration system by adding login, logout, and session management features.

**Technologies Used**
Spring Boot: Framework for building and running the web application.

Thymeleaf: Template engine for rendering HTML pages.
MySQL: Database for storing user information.
BCrypt: For password hashing.

Bootstrap: For creating a responsive user interface.

**Project Structure**

controllers
RegistrationController: Handles user registration, form validation, and saving user details securely.

ViewsController: Manages routing for login, welcome, and error pages. Also includes the logout functionality.

models
User: Defines user attributes like username, email, and hashedPassword, along with validation rules.

repositories
UserRepository: Interface for database operations, like checking if an email already exists or retrieving user data.

services
CustomUserDetails: Implements UserDetails for use with Spring Security.

CustomUserDetailsService: Loads user data from the database for authentication.

templates
register.html: Form for user registration, styled with Bootstrap.
login.html: Login form with error feedback for incorrect attempts.
welcome.html: Page displayed after a successful login.
error.html: Page shown when an error occurs.