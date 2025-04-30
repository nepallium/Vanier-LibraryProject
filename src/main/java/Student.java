import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
public class Student extends User {
    private static int nextId = 1;

    private List<NormalBook> borrowedBooks;

    public Student(String name, Gender gender, int id) {
        super(name, gender);
        this.setId(nextId++);
        this.borrowedBooks = new ArrayList<>();
    }

    /**
     * Borrows a book from the library and adds it to the student's borrowed books
     *
     * @param book
     */
    public void borrowBook(NormalBook book) {
        //TODO
    }

    /**
     * Returns a borrowed book to the library
     *
     * @param book
     */
    public boolean returnBook(NormalBook book) {
        //TODO
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
