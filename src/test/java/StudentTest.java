import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class StudentTest {
    private Student student;
    private NormalBook availableBook;
    private NormalBook issuedBook;

    @BeforeEach
    public void setUp() {
        LibrarySystem.books.clear();
        student = new Student("Jane Doe", Gender.FEMALE);
        Author auth = new Author("Auth", 40, Gender.MALE);
        availableBook = new NormalBook("Avail", auth, "ISBN-A", 123);
        LibrarySystem.books.add(availableBook);
        issuedBook = new NormalBook("Issued", auth, "ISBN-B", 200);
        issuedBook.setStatus(Issuable.Status.BORROWED);
        LibrarySystem.books.add(issuedBook);
    }

    @Test
    public void testBorrowBook_normal() {
        boolean ok = student.borrowBook(availableBook);
        Assertions.assertTrue(ok);
        Assertions.assertEquals(Issuable.Status.BORROWED, availableBook.getStatus());
        Assertions.assertTrue(student.getBorrowedBooks().contains(availableBook));
        LocalDate expected = LocalDate.now().plusWeeks(availableBook.getLoanPeriodWeeks());
        Assertions.assertEquals(expected, availableBook.getDueDate());
    }

    @Test
    public void testBorrowBook_alreadyIssued() {
        boolean ok = student.borrowBook(issuedBook);
        Assertions.assertFalse(ok);
        Assertions.assertFalse(student.getBorrowedBooks().contains(issuedBook));
    }

    @Test
    public void testBorrowBook_null_throws() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> student.borrowBook(null));
    }

    @Test
    public void testReturnBook_success() {
        student.borrowBook(availableBook);
        boolean ok = student.returnBook(availableBook);
        Assertions.assertTrue(ok);
        Assertions.assertFalse(student.getBorrowedBooks().contains(availableBook));

        Assertions.assertEquals(null, availableBook.getDueDate());

        Assertions.assertEquals(Issuable.Status.PROCESSING, availableBook.getStatus());
        Assertions.assertEquals(availableBook, LibrarySystem.returnedBooks.peek());
    }

    @Test
    public void testReturnBook_notBorrowed_returnFalse() {
        Assertions.assertFalse(student.returnBook(availableBook));
    }

    @Test
    public void testReturnBook_null_throws() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> student.returnBook(null));
    }

    @Test
    public void testGetLoansByDueDate_sortedAndNonNull() {
        NormalBook b1 = new NormalBook("B1", new Author("X", 20, Gender.MALE), "X1", 10);
        NormalBook b2 = new NormalBook("B2", new Author("X", 20, Gender.MALE), "X2", 10);
        b1.setLoanPeriodWeeks(4);
        b2.setLoanPeriodWeeks(1);
        LibrarySystem.books.add(b1);
        LibrarySystem.books.add(b2);

        student.borrowBook(b1);
        student.borrowBook(b2);

        List<NormalBook> sorted = student.getLoansByDueDate();
        Assertions.assertEquals(List.of(b2, b1), sorted);
    }

    @Test
    public void testGetLoansByDueDate_emptyList() {
        List<NormalBook> empty = student.getLoansByDueDate();
        Assertions.assertTrue(empty.isEmpty());
    }
}