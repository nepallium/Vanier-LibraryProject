import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
public class Student extends User {
    private static int nextId = 1;
    private List<NormalBook> borrowedBooks;

    public Student(String name, Gender gender) {
        super(name, gender);
        this.setId(nextId++);
        this.borrowedBooks = new ArrayList<>();
    }

    /**
     * Borrows a book from the library and adds it to the student's borrowed books
     *
     * @param book the book to borrow
     * @return
     */
    public boolean borrowBook(NormalBook book) {
        if (book == null) {
            throw new IllegalArgumentException("book must not be null");
        }

        if (!book.isAvailable() || borrowedBooks.contains(book)) {
            return false;
        }

        LocalDate due = LocalDate.now().plusWeeks(book.getLoanPeriodWeeks());
        book.setDueDate(due);

        book.setCurrentBorrower(this);
        book.setStatus(Issuable.Status.BORROWED);

        return borrowedBooks.add(book);
    }

    /**
     * Returns a borrowed book to the library
     *
     * @param book the book to return
     */
    public boolean returnBook(NormalBook book) {
        if (book == null) {
            throw new IllegalArgumentException("book must not be null");
        }

        if (borrowedBooks.remove(book)) {
            book.setStatus(Issuable.Status.PROCESSING);
            book.setDueDate(null);
            LibrarySystem.returnedBooks.offer(book);
            return true;
        }

        return false;
    }

    /**
     * Return a sorted list of borrowed books sorted by due date
     *
     * @return the sorted list of books
     */
    public List<NormalBook> getLoansByDueDate() {
        return borrowedBooks.stream()
                .filter(Objects::nonNull)
                .sorted()
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(borrowedBooks, student.borrowedBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), borrowedBooks);
    }
}
