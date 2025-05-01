import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NormalBookTest {
    private NormalBook book;

    @BeforeEach
    void setUp() {
        book = new NormalBook("Test", new Author("Auth",50,Gender.FEMALE),"ISBN",100);
    }

    // calculateLateFee()
    @Test
    void calculateLateFee_noDueDate_zeroFee() {
        book.setDueDate(null);
        assertEquals(0.0, book.calculateLateFee(), 1e-9);
    }

    @Test
    void calculateLateFee_notOverdue_zeroFee() {
        book.setDueDate(LocalDate.now().plusDays(5));
        assertEquals(0.0, book.calculateLateFee(), 1e-9);
    }

    @Test
    void calculateLateFee_normalComputed() {
        book.setDueDate(LocalDate.now().minusDays(4));
        assertEquals(4 * 0.5, book.calculateLateFee(), 1e-9);
    }

    // isAvailable()
    @Test
    void isAvailable_initiallyTrue() {
        assertTrue(book.isAvailable());
    }

    @Test
    void isAvailable_borrowed_false() {
        book.setStatus(Issuable.Status.BORROWED);
        assertFalse(book.isAvailable());
    }

    // compareTo()
    @Test
    void compareTo_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> book.compareTo(null));
    }

    @Test
    void compareTo_nonNormalBook_zero() {
        ReferenceBook rb = new ReferenceBook("R", new Author("A",20,Gender.MALE),"R1",50,"S",1);
        assertEquals(0, book.compareTo(rb));
    }

    @Test
    void compareTo_bothNullDueDates_equal() {
        book.setDueDate(null);
        NormalBook other = new NormalBook("Test", book.getAuthor(), "ISBN2", 100);
        other.setDueDate(null);
        assertEquals(0, book.compareTo(other));
    }

    @Test
    void compareTo_thisNullDueDate_positive() {
        book.setDueDate(null);
        NormalBook other = new NormalBook("X", book.getAuthor(), "I2", 10);
        other.setDueDate(LocalDate.now());
        assertTrue(book.compareTo(other) > 0);
    }

    @Test
    void compareTo_otherNullDueDate_negative() {
        book.setDueDate(LocalDate.now());
        NormalBook other = new NormalBook("X", book.getAuthor(), "I2", 10);
        other.setDueDate(null);
        assertTrue(book.compareTo(other) < 0);
    }

    @Test
    void compareTo_earlierDueDate_vsLaterDueDate() {
        NormalBook early = new NormalBook("A", book.getAuthor(), "1", 10);
        NormalBook later = new NormalBook("B", book.getAuthor(), "2", 10);
        early.setDueDate(LocalDate.now().minusDays(2));
        later.setDueDate(LocalDate.now().minusDays(5));

        assertTrue(later.compareTo(early) > 0);
        // and reverse
        assertTrue(early.compareTo(later) < 0);
    }
}
