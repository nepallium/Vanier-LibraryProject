import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Author a = new Author("alex", 15, Gender.MALE);
        ReferenceBook book = new ReferenceBook("A", a, "ISBN1", 30, "A5", 50);
        book.displayBook();

        System.out.println(5);

        NormalBook nb = new NormalBook("A", a, "ISBN1", 30);
        Student s = new Student("john", Gender.MALE);
        nb.setCurrentBorrower(s);
        nb.setDueDate(LocalDate.now());
        nb.displayBook();

    }
}
