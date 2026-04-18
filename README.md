# 🎟️ Ticket Booking CLI Application

A **Command-Line Interface (CLI)** based Ticket Booking System built using Core Java.
This application simulates real-world ticket booking workflows including user management, seat allocation, and booking validation—without relying on any external frameworks.

---

## 🚀 Features

* 👤 User registration and login via CLI
* 🎬 View available shows/events
* 🪑 Seat selection with real-time availability tracking
* ✅ Booking validation to prevent double booking
* 💾 Persistent storage using JSON (no external database)
* ⚙️ Interactive terminal-based workflow

---

## 🛠️ Tech Stack

* **Language:** Java
* **Build Tool:** Gradle
* **Data Storage:** JSON (File I/O)
* **Libraries:** Jackson (ObjectMapper)
* **Core Concepts:** OOP, Collections Framework, Java Streams

---

## 📂 Project Structure

```id="proj-struct"
Ticket-booking/
│── src/
│   ├── main/java/
│   │   ├── model/        # Entities (User, Show, Booking, Seat)
│   │   ├── service/      # Business logic
│   │   ├── repository/   # JSON read/write handling
│   │   ├── util/         # Utility classes
│   │   └── Main.java     # Entry point
│
│── data/                 # JSON files for persistence
│── build.gradle
│── README.md
```

---

## ⚙️ How to Run

### 1. Clone the repository

```bash id="clone"
git clone https://github.com/vaibhv19/Ticket-booking.git
cd Ticket-booking
```

### 2. Build the project (Gradle)

```bash id="build"
gradle build
```

### 3. Run the application

```bash id="run"
gradle run
```

---

## 💻 CLI Workflow

```bash id="workflow"
Welcome to Ticket Booking System

1. Register
2. Login
3. View Shows
4. Book Ticket
5. Exit

Enter your choice: 4

Select Show → Select Seat → Confirm Booking

Booking Successful ✅
```

---

## 🧠 Key Concepts Implemented

* Object-Oriented Design (Encapsulation, Abstraction)
* Separation of Concerns (Layered architecture)
* File-based persistence using JSON
* Data processing using Java Streams
* Efficient seat allocation and validation logic

---

## ⚡ Challenges Solved

* Preventing duplicate seat bookings through validation logic
* Managing application state without a database
* Structuring a scalable backend-like system in a CLI environment

---

## 🔮 Future Improvements

* 🌐 Convert to REST API using Spring Boot
* 🗄️ Replace JSON storage with MySQL/MongoDB
* 🔒 Add authentication with JWT
* ⚡ Implement concurrency handling for simultaneous bookings
* 🎯 Build a frontend (React)

---

## 👨‍💻 Author

**Vaibhav Gupta**
GitHub: https://github.com/vaibhv19

---

## ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub!
