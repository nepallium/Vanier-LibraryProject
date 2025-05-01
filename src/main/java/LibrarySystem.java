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
    public void issueBook(NormalBook book, Student s) {
        //TODO
    }

    /**
     * Issues book to a student based on book's isbn code and student id
     * Overloads issueBook(NormalBook book, Student s)
     *
     * @param isbn      the isbn code of the book
     * @param studentId the student's id
     */
    public void issueBook(String isbn, String studentId) {
        //TODO
    }

    /**
     * Exports all books from the library into books.csv
     */
    public void exportBooks() {
        File file = new File("src/main/resources/books.csv");
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

    private String concatenateBasicBookInfo(Book book) {
        String details = String.format("%s,%s,%s,%d,", book.getTitle(), book.getAuthor().getName(), book.getIsbn(), book.getPages());
        details += book.getStatus() + ",";
        return details;
    }

    /**
     * Exports a normal book's info into books.csv
     *
     * @param book the normal book
     */
    private void exportNormalBook(NormalBook book, File file) {
        try (FileWriter fw = new FileWriter(file, true)) {
            String details = concatenateBasicBookInfo(book);
            details += book.getCurrentBorrower().getName() + ",";
            details += book.getDueDate() + ",";

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
    private void exportReferenceBook(ReferenceBook book, File file) {
        try (FileWriter fw = new FileWriter(file, true)) {
            String details = concatenateBasicBookInfo(book);
            details += book.getShelfLocation() + ",";
            details += book.getTotalCopies() + ",";

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
    public List<Book> searchBooks(String keyword) {
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
