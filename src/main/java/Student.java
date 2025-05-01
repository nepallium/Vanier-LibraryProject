import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
        //TODO set due date once borrowed
        return true;
    }

    /**
     * Returns a borrowed book to the library
     *
     * @param book the book to return
     */
    public boolean returnBook(NormalBook book) {
        //TODO mark book as available
        return true;
    }

    /**
     * Return a sorted list of borrowed books sorted by due date
     *
     * @return the sorted list of books
     */
    public List<NormalBook> getLoansByDueDate() {
        //TODO
        return null;
    }
}
