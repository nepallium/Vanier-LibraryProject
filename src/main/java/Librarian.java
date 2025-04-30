import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Librarian extends User {
    private static int nextId = 1;

    public Librarian(String name, Gender gender) {
        super(name, gender);
        this.setId(nextId++);
    }

    /**
     * Adds new books from an input csv file into the library's list of books
     *
     * @param path
     */
    public void addNewBooksToLibrary(String path) {
        //TODO
    }

    /**
     * Processes a returned book from the library's queue of returned books to make it available again
     */
    public void processReturn() {
        //TODO
    }
}
