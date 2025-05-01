import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianTest {
    private Librarian lib;

    @BeforeEach
    public void setUp() {
        lib = new Librarian("Libby", Gender.FEMALE);
        LibrarySystem.books.clear();
        LibrarySystem.returnedBooks.clear();
    }

    @Test
    public void testProcessReturn_emptyQueue_nothingHappens() {
        // nothing in queue
        assertTrue(LibrarySystem.returnedBooks.isEmpty());
        lib.processReturn();
        // still nothing
        assertTrue(LibrarySystem.returnedBooks.isEmpty());
    }

    @Test
    public void testProcessReturn_singleBook_makesAvailable() {
        NormalBook nb = new NormalBook("Book1", new Author("A", 30, Gender.MALE), "I1", 100);
        nb.setStatus(Issuable.Status.BORROWED);
        LibrarySystem.returnedBooks.offer(nb);

        lib.processReturn();

        assertEquals(Issuable.Status.AVAILABLE, nb.getStatus());
        assertTrue(LibrarySystem.returnedBooks.isEmpty());
    }

    @Test
    public void testProcessReturn_multipleBooks_processesOneAtATime() {
        NormalBook b1 = new NormalBook("Book1", new Author("A", 30, Gender.MALE), "I1", 100);
        NormalBook b2 = new NormalBook("Book2", new Author("B", 40, Gender.FEMALE), "I2", 200);
        b1.setStatus(Issuable.Status.BORROWED);
        b2.setStatus(Issuable.Status.BORROWED);
        LibrarySystem.returnedBooks.offer(b1);
        LibrarySystem.returnedBooks.offer(b2);

        // first call
        lib.processReturn();
        assertEquals(Issuable.Status.AVAILABLE, b1.getStatus());
        assertEquals(Issuable.Status.BORROWED, b2.getStatus());
        assertEquals(1, LibrarySystem.returnedBooks.size());

        // second call
        lib.processReturn();
        assertEquals(Issuable.Status.AVAILABLE, b2.getStatus());
        assertTrue(LibrarySystem.returnedBooks.isEmpty());
    }

    @Test
    public void testAddNewBooksToLibrary_normalCsv_importsAll() throws Exception {
        File file = new File("src/test/resources/normal_books.csv");
        lib.addNewBooksToLibrary(file.toString());

        assertEquals(2, LibrarySystem.books.size());
        assertTrue(LibrarySystem.books.stream().anyMatch(b -> "ISBN1".equals(b.getIsbn())));
        assertTrue(LibrarySystem.books.stream().anyMatch(b -> "ISBN2".equals(b.getIsbn())));
    }

    @Test
    public void testAddNewBooksToLibrary_malformedLines_ignoresInvalid() throws Exception {
        File file = new File("src/test/resources/malformed_books.csv");
        lib.addNewBooksToLibrary(file.toString());

        assertEquals(2, LibrarySystem.books.size());
        assertTrue(LibrarySystem.books.stream().anyMatch(b -> "ISBN1".equals(b.getIsbn())));
        assertTrue(LibrarySystem.books.stream().anyMatch(b -> "ISBN2".equals(b.getIsbn())));
    }

    @Test
    public void testAddNewBooksToLibrary_trimValues_importsTrimmed() throws Exception {
        File file = new File("src/test/resources/trimmed_books.csv");
        lib.addNewBooksToLibrary(file.toString());

        assertEquals(2, LibrarySystem.books.size());
        assertTrue(LibrarySystem.books.stream().anyMatch(b -> "ISBN1".equals(b.getIsbn())));
        assertTrue(LibrarySystem.books.stream().anyMatch(b -> "ISBN2".equals(b.getIsbn())));
    }
}
