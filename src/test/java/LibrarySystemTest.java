import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

public class LibrarySystemTest {
    private NormalBook normalBook;
    private ReferenceBook referenceBook;
    private Student student;

    @BeforeEach
    public void setUp() {
        LibrarySystem.books.clear();
        LibrarySystem.students.clear();

        // create one loanable and one non-loanable book
        Author auth = new Author("Auth", 40, Gender.MALE);
        normalBook = new NormalBook("Normal", auth, "ISBN-N", 100);
        referenceBook = new ReferenceBook("Reference", auth, "ISBN-R", 200, "Shelf A", 5);

        LibrarySystem.books.add(normalBook);
        LibrarySystem.books.add(referenceBook);

        // create and register a student
        student = new Student("Jeremy", Gender.FEMALE);
        LibrarySystem.students.add(student);
    }

    @Test
    void issueBookByObject_returnsTrue() {
        boolean ok = LibrarySystem.issueBook(normalBook, student);
        Assertions.assertTrue(ok, "can borrow a normal book when it's available");
        Assertions.assertEquals(Issuable.Status.BORROWED, normalBook.getStatus());
        Assertions.assertTrue(student.getBorrowedBooks().contains(normalBook));
    }

    @Test
    void issueBookByObject_returnsFalseWhenAlreadyBorrowed() {
        LibrarySystem.issueBook(normalBook, student);
        boolean ok = LibrarySystem.issueBook(normalBook, student);
        Assertions.assertFalse(ok, "cannot borrow same book twice");
    }

    @Test
    void issueBookByIsbn_returnsTrue() {
        boolean ok = LibrarySystem.issueBook("ISBN-N", student.getId());
        Assertions.assertTrue(ok, "can borrow book by isbn");
        Assertions.assertEquals(Issuable.Status.BORROWED, normalBook.getStatus());
        Assertions.assertTrue(student.getBorrowedBooks().contains(normalBook));
    }

    @Test
    void issueBookByIsbn_returnsFalseForReferenceBook() {
        boolean ok = LibrarySystem.issueBook("ISBN-R", student.getId());
        Assertions.assertFalse(ok, "Should return false when attempting to borrow a non-loanable ReferenceBook");
        Assertions.assertFalse(student.getBorrowedBooks().contains(referenceBook));
    }

    @Test
    public void testSearchBooks_normal() {
        NormalBook b1 = new NormalBook("Java Basics", new Author("A", 30, Gender.MALE), "J1", 100);
        NormalBook b2 = new NormalBook("Advanced Java", new Author("B", 40, Gender.FEMALE), "J2", 200);
        LibrarySystem.books.add(b1);
        LibrarySystem.books.add(b2);

        List<Book> result = LibrarySystem.searchBooks("java");
        List<Book> expected = List.of(b1, b2);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testSearchBooks_noMatches() {
        NormalBook b = new NormalBook("Python Intro", new Author("C", 25, Gender.MALE), "P1", 150);
        LibrarySystem.books.add(b);

        List<Book> result = LibrarySystem.searchBooks("c++");
        List<Book> expected = List.of();

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testSearchBooks_nullKeyword_throws() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LibrarySystem.searchBooks(null);
        });
    }

    @Test
    public void testSearchBooks_blankKeyword_throws() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LibrarySystem.searchBooks("   ");
        });
    }

    @Test
    public void testConcatenateBasicBookInfo_valid() throws Exception {
        Author auth = new Author("Auth", 50, Gender.FEMALE);
        NormalBook nb = new NormalBook("Title", auth, "ISBN", 123);
        nb.setStatus(Issuable.Status.AVAILABLE);

        Method m = LibrarySystem.class.getDeclaredMethod("concatenateBasicBookInfo", Book.class);
        m.setAccessible(true);
        String result = (String) m.invoke(null, nb);
        String expected = "Title,Auth,ISBN,123,";

        Assertions.assertEquals(expected, result);
    }
}
