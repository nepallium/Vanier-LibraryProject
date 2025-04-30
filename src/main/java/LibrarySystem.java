import java.util.*;

public class LibrarySystem {
    public static List<Book> books;
    public static List<Librarian> librarians;
    public static List<Student> students;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        welcomeMenu();
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
