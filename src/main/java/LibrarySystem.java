import java.util.*;

public class LibrarySystem {
    public static List<Book> books = new LinkedList<>();
    public static Queue<NormalBook> returnedBooks = new LinkedList<>();
    public static List<Librarian> librarians = new ArrayList<>();
    public static List<Student> students = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Issues book to a student based on book object and student object
     * Overloads issueBook(String isbn, String studentId)
     *
     * @param book the book to issue
     * @param s    the borrower student
     */
    public void issueBook(NormalBook book, Student s) {
        //TODO
    }

    /**
     * Issues book to a student based on book's isbn code and student id
     * Overloads issueBook(NormalBook book, Student s)
     *
     * @param isbn      the isbn code of the book
     * @param studentId the student's id
     */
    public void issueBook(String isbn, String studentId) {
        //TODO
    }

    /**
     * Exports all books from the library into books.csv
     */
    public void exportBooks() {
        for (Book book : books) {
            if (book instanceof NormalBook nb) {
                exportNormalBook(nb);
            } else if (book instanceof ReferenceBook rb) {
                exportReferenceBook(rb);
            }
        }
    }

    /**
     * Exports a normal book's info into books.csv
     *
     * @param book the normal book
     */
    private void exportNormalBook(NormalBook book) {
        //TODO
    }

    /**
     * Exports a reference book's info into books.csv
     *
     * @param book
     */
    private void exportReferenceBook(ReferenceBook book) {
        //TODO
    }

    /**
     * Gives a list of books that contain a specified keyword, case-insensitive
     *
     * @param keyword the keyword to look for
     * @return the list of books containing the keyword
     */
    public List<Book> searchBooks(String keyword) {
        String kw = keyword.toLowerCase().trim();

        return books.stream()
                .filter(Objects::nonNull)
                .filter(book -> book.getTitle().toLowerCase().contains(kw))
                .toList();
    }

    public static void welcomeMenu() {
        while (true) {
            System.out.println("--- Welcome to the Vanier Library ---");
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println("0. Exit");
            System.out.println("Choose an option");

            String option = scanner.next();

            switch (option) {
                case "1":
                    login();
                    return;
                case "2":
                    signup();
                    return;
                case "0":
                    System.out.println("See you next time!");
                    return;
                default:
                    System.out.println("\nInvalid option.\n");
            }
        }
    }

    public static void login() {
    }

    public static void signup() {
    }

    /**
     * Validates whether the name contains only letters.
     * True if name is valid, false otherwise.
     *
     * @param name the input name
     * @return whether the name is alphabetic
     */
    private static boolean validateName(String name) {
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}
