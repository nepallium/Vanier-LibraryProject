import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
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
        //TODO
    }

    /**
     * Issues book to a student based on book's isbn code and student id
     * Overloads issueBook(NormalBook book, Student s)
     *
     * @param isbn      the isbn code of the book
     * @param studentId the student's id
     */
    public static void issueBook(String isbn, String studentId) {
        //TODO
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
     * Builds first common CSV columns for any book
     *
     * @param book the book
     * @return title,author,isbn,pages,
     */
    private static String concatenateBasicBookInfo(Book book) {
        if (book.getAuthor() == null) {
            throw new IllegalArgumentException("book.author must not be null");
        }

        return String.format(
                "%s,%s,%s,%d,%s,",
                book.getTitle(),
                book.getAuthor().getName(),
                book.getIsbn(),
                book.getPages()
        );
    }

    /**
     * Exports a normal book's info into books.csv
     *
     * @param book the normal book
     */
    private static void exportNormalBook(NormalBook book, File file) {
        String borrower = (book.getCurrentBorrower() != null)
                ? book.getCurrentBorrower().getName() : "";
        String dueDate = (book.getDueDate() != null)
                ? book.getDueDate().toString() : "";

        String details = concatenateBasicBookInfo(book)
                + borrower + "," + dueDate + ",";

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(details + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Exports a reference book's info into books.csv
     *
     * @param book
     */
    private static void exportReferenceBook(ReferenceBook book, File file) {
        String details = concatenateBasicBookInfo(book)
                + book.getShelfLocation() + ","
                + book.getTotalCopies() + ",";

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(details + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gives a list of books that contain a specified keyword, case-insensitive
     *
     * @param keyword the keyword to look for
     * @return the list of books containing the keyword
     */
    public static List<Book> searchBooks(String keyword) {
        if (keyword == null) {
            throw new InvalidParameterException();
        }

        String kw = keyword.trim().toLowerCase();

        return books.stream()
                .filter(Objects::nonNull)
                .filter(book -> book.getTitle().toLowerCase().contains(kw))
                .toList();
    }
}
