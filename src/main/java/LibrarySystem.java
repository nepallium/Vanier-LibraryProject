import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
     */
    public static void issueBook(NormalBook book, Student s) {
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

        s.borrowBook(book);
    }

    /**
     * Issues book to a student based on book's isbn code and student id
     * Overloads issueBook(NormalBook book, Student s)
     *
     * @param isbn      the isbn code of the book
     * @param studentId the student's id
     */
    public static void issueBook(String isbn, int studentId) {
        if (isbn == null) {
            throw new IllegalArgumentException("isbn must not be null");
        }

        NormalBook bookToIssue = null;
        for (Book book : books) {
            if (book instanceof NormalBook nb && book.getIsbn().equals(isbn)) {
                bookToIssue = nb;
                break;
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

        issueBook(bookToIssue, s);
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
}
