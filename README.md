# Library Management System

## Project Description (Deliverable 1)

### 1. Scenario
The Vanier College Library needs a simple system that can manage its book catalog and track student's book exchanges. The system needs to allow students to search and borrow books, and let librarians add, issue, and return them.

### 2. Design Paradigm
Functionalities that will be demonstrated:
- Class hierarchies and OOP
- Interfaces
- Runtime polymorphism (overriding and overloading)
- Java collections: `List`, `Queue`
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
  - Search the catalog using a keyword
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
    | Normal    | Book issue status, expected return date      |
    | Reference | Shelf location, specify book is not issuable |
- **Overloading**
  - In `LibrarySystem`, we provide two ways to lend:
``` java
public boolean issueBook(NormalBook book, Student s)
```
```java
public boolean issueBook(String isbn, String studentId)
```

### 7. Text I/O
- `addNewBooksToLibrary(String path)` in `Librarian` that reads a CSV file and adds all books to a the `LibrarySystem`'s list of books
  - Provides an efficient alternative to add books to the library. Only accessible by `Librarian`s
- `exportBooks()` in `LibrarySystem` that exports all current books into a CSV file
  - Allows easy overview of current books. Librarians could also use this to quickly delete a book by removing a line from the CSV, then running the above method to update the list.

### 8. Comparable and Comparator
``` java
public class NormalBook implements Comparable<NormalBook>
```
- This will compare books based on their due date, useful for students

``` java
public static class BookComparator implements Comparator<Book>
```
- This will compare books based on either title, pages, author, or isbn (default)

### 9. UML Class Diagram
- Navigate to `doc/uml_class_diagram.pdf`

### 10. Deliverable 2
For Deliverable 2:
- All class and interface definitions
- Documentation for methods
- JUnit tests
- Partial implementations:
  - Basic CSV read/write
  - Book display

### 11. Deliverable 3
Complete implementation of all methods

### 12. Deliverable 4
Project report
- Navigate to `doc/project_report.pdf`
