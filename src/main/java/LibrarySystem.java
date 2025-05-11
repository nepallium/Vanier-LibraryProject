import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class LibrarySystem {
    public static List<Book> books = new LinkedList<>();
    public static Queue<NormalBook> returnedBooks = new LinkedList<>();
    public static List<Librarian> librarians = new ArrayList<>();
    public static List<Student> students = new ArrayList<>();

    /**
     * Issues book to a student based on book object and student object
     * Overloads issueBook(String isbn, String studentId)
     *
     * @param book the book to issue
     * @param s    the borrower student
     * @return whether the book has been successfully borrowed or not (ie not already borrowed)
     */
    public static boolean issueBook(NormalBook book, Student s) {
        if (book == null) {
            throw new IllegalArgumentException("book must not be null");
        }
        if (s == null) {
            throw new IllegalArgumentException("student must not be null");
        }
        if (!books.contains(book)) {
            throw new IllegalArgumentException("book is not in library");
        }
        if (!students.contains(s)) {
            throw new IllegalArgumentException("student is not registered");
        }

        return s.borrowBook(book);
    }

    /**
     * Issues book to a student based on book's isbn code and student id
     * Overloads issueBook(NormalBook book, Student s)
     *
     * @param isbn      the isbn code of the book
     * @param studentId the student's id
     * @return whether the book has been successfully borrowed or not (ie not already borrowed)
     */
    public static boolean issueBook(String isbn, int studentId) {
        if (isbn == null) {
            throw new IllegalArgumentException("isbn must not be null");
        }

        NormalBook bookToIssue = null;
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book instanceof NormalBook nb) {
                    bookToIssue = nb;
                    break;
                } else {
                    return false;
                }
            }
        }
        if (bookToIssue == null) {
            throw new IllegalArgumentException("isbn is not in book library");
        }

        Student s = null;
        for (Student student : students) {
            if (student != null && student.getId() == studentId) {
                s = student;
                break;
            }
        }
        if (s == null) {
            throw new IllegalArgumentException("student is not registered in library");
        }

        return issueBook(bookToIssue, s);
    }

    /**
     * Exports all books from the library into books.csv
     */
    public static void exportBooks() {
        File file = new File("src/main/resources/books.csv");

//        Clear the file
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Book book : books) {
            if (book instanceof NormalBook nb) {
                exportNormalBook(nb, file);
            } else if (book instanceof ReferenceBook rb) {
                exportReferenceBook(rb, file);
            }
        }
    }

    /**
     * Exports a normal book's info into books.csv
     * title,author,isbn,pages,status,borrower,dueDate
     *
     * @param book the normal book
     */
    private static void exportNormalBook(NormalBook book, File file) {
        String borrower = (book.getCurrentBorrower() != null)
                ? book.getCurrentBorrower().getName() : "";
        String dueDate = (book.getDueDate() != null)
                ? book.getDueDate().toString() : "";

        String details = concatenateBasicBookInfo(book);
        details += book.getStatus() + "," + borrower + "," + dueDate;

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(details + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Exports a reference book's info into books.csv
     * title,author,isbn,pages,shelfLocation,totalCopies
     *
     * @param book
     */
    private static void exportReferenceBook(ReferenceBook book, File file) {
        String details = concatenateBasicBookInfo(book)
                + book.getShelfLocation() + ","
                + book.getTotalCopies();

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(details + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds first common CSV columns for any book
     *
     * @param book the book
     * @return title,author,isbn,pages
     */
    private static String concatenateBasicBookInfo(Book book) {
        if (book.getAuthor() == null) {
            throw new IllegalArgumentException("book.author must not be null");
        }
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("book.title must not be null or blank");
        }
        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            throw new IllegalArgumentException("book.isbn must not be null or blank");
        }

        return String.format(
                "%s,%s,%s,%d,",
                book.getTitle(),
                book.getAuthor().getName(),
                book.getIsbn(),
                book.getPages()
        );
    }

    /**
     * Gives a list of books that contain a specified keyword, case-insensitive
     *
     * @param keyword the keyword to look for
     * @return the list of books containing the keyword
     */
    public static List<Book> searchBooks(String keyword) {
        if (keyword == null) {
            throw new IllegalArgumentException("keyword must not be null");
        }

        String kw = keyword.trim().toLowerCase();

        if (kw.isEmpty()) {
            throw new IllegalArgumentException("keyword must not be empty");
        }

        return books.stream()
                .filter(Objects::nonNull)
                .filter(book -> book.getTitle().toLowerCase().contains(kw))
                .toList();
    }

    /**
     * Demo for Deliverable 4
     */
    public static void main(String[] args) {
        // Instantiate a Librarian and a Student
        Librarian librarian = new Librarian("Alice", Gender.FEMALE);
        librarians.add(librarian);
        Student student = new Student("Bob", Gender.MALE);
        students.add(student);

        // Import books from CSV
        System.out.println("=== Importing books from CSV ===");
        librarian.addNewBooksToLibrary("src/main/resources/new_books.csv");

        // Print all books currently in library
        // books.csv also updated
        System.out.println("\n=== Current Catalog ===");
        books.forEach(Book::displayBook);

        // Student borrows book by ISBN
        System.out.println("\n=== Bob borrows ISBN 123-456 ===");
        issueBook("123-456", student.getId());

        // Student borrows book by NormalBook object
        System.out.println("\n=== Bob borrows Data Structures ===");
        issueBook((NormalBook) books.get(1), student);

//        // Borrowing a ReferenceBook yields an error
//        System.out.println("\n=== Bob borrows ReferenceBook: ISBN 0123 ===");
//        issueBook("0123", student.getId());

        // Bob cannot borrow same book twice
        System.out.println("\n=== Cannot borrow same book twice ===");
        System.out.println("Successfully borrowed?: " + issueBook((NormalBook) books.get(1), student));

        // Show Bob’s borrowed books and updated catalog status
        System.out.println("\n=== Bob’s Borrowed Books ===");
        student.getBorrowedBooks()
                .forEach(Book::displayBook);
        System.out.println("\n=== Catalog After Borrow ===");
        books.forEach(Book::displayBook);

        // Sort Bob's books by due date
        System.out.println("\n=== Sort by Due Date ===");
        // Set first borrowed book's due date to a later date than the second
        student.getBorrowedBooks().getFirst().setDueDate(LocalDate.now().plusMonths(2));
        student.getLoansByDueDate().forEach(Book::displayBook);

        // Student returns Intro to Java book
        System.out.println("\n=== Bob returns ISBN 123-456 ===");
        boolean returned = student.returnBook(student.getBorrowedBooks().getFirst());
        System.out.println("Return successful? " + returned);

        // Show Bob’s updated borrowed books and updated catalog status
        System.out.println("\n=== Bob’s Borrowed Books ===");
        student.getBorrowedBooks()
                .forEach(Book::displayBook);
        System.out.println("\n=== Catalog After Borrow ===");
        books.forEach(Book::displayBook);

        // Alice, librarian, processes the return
        System.out.println("\n=== Alice processes return ===");
        librarian.processReturn();

        // Search catalog by keyword (“data”)
        System.out.println("\n=== Search by keyword “data” ===");
        List<Book> results = searchBooks("data");
        results.forEach(Book::displayBook);

        // Sort catalog of books by title
        System.out.println("\n=== Sort by Title ===");
        books.sort(new Book.BookComparator("title"));
        books.forEach(Book::displayBook);

        // Export current catalog back to CSV (now sorted)
        System.out.println("\n=== Exporting catalog to CSV ===");
        exportBooks();
        System.out.println("Export completed.");

        // Calculate late fee
        System.out.println("\n=== Calculate late fee ===");
        NormalBook bookWithLateFee = student.getBorrowedBooks().getFirst();
        bookWithLateFee.setDueDate(LocalDate.now().minusDays(5)); // Set imaginary overdue date
        System.out.printf("5 day late fee: %.2f$", bookWithLateFee.calculateLateFee());
    }
}
