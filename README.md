# Online Examination Module

## Project Overview
The Online Examination Module is a comprehensive platform designed for conducting MCQ and programming-based exams. It consists of three main folders:

- **adminfrontend**: Admin interface for managing exams, questions, users and viewing results.
- **frontend**: Student interface for taking exams.
- **backend**: Spring Boot application handling business logic, data storage, and APIs.

## Technologies Used
- **Frontend**: Vite + React JS, Material UI, Tailwind CSS
- **Backend**: Spring Boot

## Features

### Admin Features
- Add and manage MCQ and programming questions.
- Create exams by specifying the number of questions per category.
- Set exam configurations such as duration, difficulty, and passing criteria.
- Analyze results and monitor student performance.

### Student Features
- Take exams with MCQ and programming questions.
- Full-screen mode during exams with restrictions on copy-paste.
- Countdown timer for exams.
- Paginated question navigation.
- Submit answers.

### Security Features
- Role-based access control (Admin and Student).
- Environment variables for sensitive data.

## Folder Structure

### adminfrontend
- **Purpose**: Admin panel for managing questions, exams, users and viewing results.
- **Technologies**: Vite + React JS, Material UI, Tailwind CSS

### frontend
- **Purpose**: Student interface for taking exams.
- **Technologies**: Vite + React JS, Material UI, Tailwind CSS

### backend
- **Purpose**: Handles APIs, data storage, and business logic.
- **Technologies**: Spring Boot

## Environment Variables

### Backend
- `DB_PASSWORD`: Database password.
- `DB_URL`: URL of the database.
- `DB_USERNAME`: Database username.
- `judge0.api.key`: API key for Judge0 (used for programming question evaluation).
- `MAIL_PASSWORD`: Password for sending emails.
- `SECRET_KEY`: Secret key for token generation.

### Frontend (adminfrontend and frontend)
- `VITE_API_URL`: API base URL for connecting to the backend.

## How to Run the Project

### Backend
1. Clone the repository and navigate to the `backend` folder.
2. Configure the environment variables in the application.
3. Run the application using:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Backend will be available at `http://localhost:8080`.

### Frontend
1. Clone the repository and navigate to the respective frontend folder (`adminfrontend` or `frontend`).
2. Configure the `VITE_API_URL` environment variable in a `.env` file:
   ```env
   VITE_API_URL=http://localhost:8080
   ```
3. Install dependencies and start the development server:
   ```bash
   npm install
   npm run dev
   ```
4. Frontend will be available at `http://localhost:5173`.

## Contributing
Contributions are welcome! Please create a pull request with detailed descriptions of the changes.

## Contact
For any queries, please contact the project maintainer at akshayvaghasiya3636@gmail.com.
