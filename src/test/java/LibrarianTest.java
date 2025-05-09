import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianTest {
    private Librarian librarian;

    @BeforeEach
    public void setUp() {
        librarian = new Librarian("Libby", Gender.FEMALE);
        LibrarySystem.books.clear();
        LibrarySystem.returnedBooks.clear();
    }

    @Test
    public void testProcessReturn_emptyQueue_nothingHappens() {
        // nothing in queue
        assertTrue(LibrarySystem.returnedBooks.isEmpty());
        librarian.processReturn();
        // still nothing
        assertTrue(LibrarySystem.returnedBooks.isEmpty());
    }

    @Test
    public void testProcessReturn_singleBook_makesAvailable() {
        NormalBook nb = new NormalBook("Book1", new Author("A", 30, Gender.MALE), "I1", 100);
        nb.setStatus(Issuable.Status.BORROWED);
        LibrarySystem.returnedBooks.offer(nb);

        librarian.processReturn();

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
        librarian.processReturn();
        assertEquals(Issuable.Status.AVAILABLE, b1.getStatus());
        assertEquals(Issuable.Status.BORROWED, b2.getStatus());
        assertEquals(1, LibrarySystem.returnedBooks.size());

        // second call
        librarian.processReturn();
        assertEquals(Issuable.Status.AVAILABLE, b2.getStatus());
        assertTrue(LibrarySystem.returnedBooks.isEmpty());
    }

    @Test
    public void testAddNewBooksToLibrary_normalCsv_importsAll() {
        File file = new File("src/test/resources/normal_books.csv");
        librarian.addNewBooksToLibrary(file.toString());

        assertEquals(3, LibrarySystem.books.size());
        assertTrue(LibrarySystem.books.stream()
                .anyMatch(b -> "ISBN1".equals(b.getIsbn())));
        assertTrue(LibrarySystem.books.stream()
                .anyMatch(b -> "ISBN2".equals(b.getIsbn())));
        assertTrue(LibrarySystem.books.stream()
                .anyMatch(b -> "ISBN3".equals(b.getIsbn())));

        long normalCount = LibrarySystem.books.stream()
                .filter(b -> b instanceof NormalBook)
                .count();
        assertEquals(3, normalCount, "imported books should be normalbooks");
    }

    @Test
    public void testAddNewBooksToLibrary_malformedLines_importsValid() {
        File file = new File("src/test/resources/malformed_books.csv");
        librarian.addNewBooksToLibrary(file.toString());

        assertEquals(3, LibrarySystem.books.size());

        long normalCount = LibrarySystem.books.stream()
                .filter(b -> b instanceof NormalBook)
                .count();
        long refCount = LibrarySystem.books.stream()
                .filter(b -> b instanceof ReferenceBook)
                .count();
        assertEquals(2, normalCount, "2 normalbooks");
        assertEquals(1, refCount,    "1 referencebook");

        // verify ReferenceBook fields
        for (Book b : LibrarySystem.books) {
            if (b instanceof ReferenceBook rb) {
                assertEquals("ShelfC", rb.getShelfLocation());
                assertEquals(5, rb.getTotalCopies());
            }
        }
    }

    @Test
    public void testAddNewBooksToLibrary_trimmed_importsValid() {
        File file = new File("src/test/resources/trimmed_books.csv");
        librarian.addNewBooksToLibrary(file.toString());

        assertEquals(3, LibrarySystem.books.size());

        long normalCount = LibrarySystem.books.stream()
                .filter(b -> b instanceof NormalBook)
                .count();
        long refCount = LibrarySystem.books.stream()
                .filter(b -> b instanceof ReferenceBook)
                .count();
        assertEquals(2, normalCount, "2 normalbooks");
        assertEquals(1, refCount,    "1 ReferenceBook");

        // verify ReferenceBook fields
        for (Book b : LibrarySystem.books) {
            if (b instanceof ReferenceBook rb) {
                assertEquals("ShelfB", rb.getShelfLocation());
                assertEquals(2, rb.getTotalCopies());
            }
        }
    }
}
