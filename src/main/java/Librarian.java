import lombok.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
public class Librarian extends User {
    private static int nextId = 1;

    public Librarian(String name, Gender gender) {
        super(name, gender);
        this.setId(nextId++);
    }

    /**
     * Adds new books from an input csv file into the library's list of books
     *
     * @param path the path to read from
     */
    public void addNewBooksToLibrary(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("path cannot be null nor empty");
        }

        File file = new File(path);
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(",", -1);

                if (parts.length >= 4) {
                    parts = Arrays.stream(parts)
                            .map(String::trim)
                            .toArray(String[]::new);
                    String title = parts[0];
                    Author author = new Author(parts[1]);
                    String isbn = parts[2];
                    int pages = Integer.parseInt(parts[3]);

                    Book book;
                    if (parts.length == 7) {
                        book = new NormalBook(title, author, isbn, pages);
                    } else if (parts.length == 6) {
                        String shelfLocation = parts[4];
                        int totalCopies = Integer.parseInt(parts[5]);
                        book = new ReferenceBook(title, author, isbn, pages, shelfLocation, totalCopies);
                    } else {
                        continue;
                    }

                    boolean duplicateExists = false;
                    for (Book b : LibrarySystem.books) {
                        if (b.equals(book)) {
                            duplicateExists = true;
                            break;
                        }
                    }

                    if (!duplicateExists) {
                        LibrarySystem.books.add(book);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        LibrarySystem.exportBooks();
    }

    /**
     * Processes a returned book from the library's queue of returned books to make it available again
     */
    public void processReturn() {
        NormalBook returnedBook = LibrarySystem.returnedBooks.poll();
        if (returnedBook != null) {
            returnedBook.setCurrentBorrower(null);
            returnedBook.setDueDate(null);
            returnedBook.setStatus(Issuable.Status.AVAILABLE);

            LibrarySystem.exportBooks();
        }
    }
}
