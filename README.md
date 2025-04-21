# Library Management System

## Project Description (Deliverable 1)

### 1. Scenario
The Vanier College Library needs a simple system that can manage its book catalog and track student's book exchanges. The system needs to allow students to search and borrow books, and let librarians add, issue, and return them.

### 2. Design Paradigm
Functionalities that will be demonstrated:
- Class hierarchies and OOP
- Interfaces
- Runtime polymorphism (overriding and overloading)
- Java collections: `Map`, `List`, `Set`, `Queue`
- File I/O with CSV files
- `Comparable` and `Comparator` for sorting
- Test-Driven Development with JUnit tests
- Git version control

### 3. Expected Output
- **Librarian** 
  - Add/remove books from the library
  - Issue books to a student
  - Process returns (i.e. make the book available for borrowing again).
- **Students** 
  - Search the catalog by title/author/year
  - Borrow books (if available)
  - Return previously borrowed books
- Changes should be reflected in the appropriate CSV files

### 4. Class Hierarchies
- **Hierarchy 1:** `User` (abstract) → `Student`, `Librarian`
- **Hierarchy 2:** `Book` (abstract) → `NormalBook`, `ReferenceBook`

### 5. Interface
**`Issuable` interface**:
- Contract to keep track of the student who returns the book and the student who is borrowing the book
- Implemented by `NormalBook`, but **not** by `ReferenceBook` (e.g. dictionary).
  - Future-proof way add a new loanable resource type (DVD, equipment, board game, etc.) by implementing `Issuable` to inherit checkout/return behavior without touching existing class hierarchy

### 6. Runtime Polymorphism
- **Overriding**
  - Common `displayBook()` in the abstract `Book` class
  - | Book      | Display info                                 |
    |-----------|----------------------------------------------|
    | Normal    | Available copies, expected return date       |
    | Reference | Shelf location, specify book is not issuable |
- **Overloading**
  - In `Librarian`, we provide two ways to lend:
``` java
public boolean issueBook(NormalBook book, Student s) {}
```
```java
public boolean issueBook(String isbn, String studentId) {}
```

### 7. Text I/O
- `loadBooks(String path)` in `Librarian` that reads a CSV file and adds all books to a `List`
  - Provides an efficient alternative to add books to the library. Only accessible by `Librarian`s
- `exportBooks()` in `Book` that exports all current books into a CSV file
  - Allows easy overview of current books. Librarians could also use this to quickly delete a book by removing a line from the CSV. 

### 8. Comparable and Comparator
``` java
public class NormalBook implements Comparable<NormalBook>
```
- This will compare books based on their due date, useful for students

``` java
public class Book implements Comparator<Book>
```
- This will compare books based on either name, pages, author, or year 

### 9. UML Class Diagram
- Navigate to `doc/uml_class_diagram`

### 10. Deliverable 2
For Deliverable 2, I will roughly deliver:
- All class and interface definitions
- Documentation for methods
- JUnit tests
- Partial implementations:
  - Basic loan and return logic for books
  - Basic CSV read/write
  - User login
