[README_Library.md](https://github.com/user-attachments/files/29081343/README_Library.md)
# Library Management System — Java

A console-based Java application for managing basic library operations, developed as part of the OOP1 course project at Université Antonine. The system applies Object-Oriented Programming principles and supports both file-based and MongoDB-based data storage.

---

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Data Storage](#data-storage)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Technical Details](#technical-details)
- [Bonus Features](#bonus-features)
- [Authors](#authors)

---

## Features

- Add new books to the library
- View all available books
- Borrow a book
- Return a borrowed book
- Save and load book data from a file
- Data persists between program executions

---

## Project Structure

```
library-management-system/
│
├── src/
│   ├── Main.java
│   ├── Book.java
│   ├── Library.java
│   └── FileHandler.java
│
├── data/
│   └── books.txt
│
└── README.md
```

---

## Data Storage

The system supports two storage modes:

**File-based (default):** Data is stored in `books.txt` using a structured CSV format. Each record contains the Book ID, title, author, and borrow status. No database configuration is needed.

**MongoDB (bonus):** The system can alternatively store data in a MongoDB NoSQL database, replacing the text file entirely.

Both modes are supported in this implementation.

---

## Getting Started

### Prerequisites
- Java JDK 8 or later
- If using MongoDB: MongoDB server running locally and the MongoDB Java Driver added to the project

### Compile and Run

```
javac src/*.java
java -cp src Main
```

---

## Usage

Once the program starts, a console menu appears:

```
=== Library Management System ===
1. Add a book
2. View all books
3. Borrow a book
4. Return a book
0. Exit
```

All changes are saved automatically to the data file (or database) after each operation.

---

## Technical Details

The project is built around three main classes:

- **Book** — represents a book with its ID, title, author, and borrow status
- **Library** — manages the collection of books and all operations (add, view, borrow, return)
- **FileHandler** — handles reading from and writing to the storage file
- **Main** — provides the console menu and user interaction

OOP principles applied: encapsulation, abstraction, and modular class design.

---

## Bonus Features

- MongoDB integration as an alternative to file-based storage
- Both txt file and MongoDB storage are supported simultaneously in this implementation

---

## Authors

| Name | Role |
|---|---|
|Mariam khalife| Developer |

Université Antonine — OOP1 Project, 2025-2026

---

## Academic Integrity

This project was developed as part of a graded academic assignment. Plagiarism is strictly prohibited. Any submission must represent the student's own original work.
