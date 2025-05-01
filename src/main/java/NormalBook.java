import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class NormalBook extends Book implements Issuable {
    private Student currentBorrower;
    private LocalDate dueDate;
    private int loanPeriodWeeks = 4;
    private static final double DAILY_FEE = 0.5;

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public NormalBook(String title, Author author, String isbn, int pages) {
        super(title, author, isbn, pages);
    }

    /**
     * Calculates the fee a user needs to pay due to returning it past the due date based on the daily fee
     *
     * @return the overdue fee to pay
     */
    @Override
    public double calculateLateFee() {
        if (dueDate == null) {
            return 0.0;
        }

        long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        if (daysOverdue <= 0) {
            return 0.0;
        }
        return daysOverdue * DAILY_FEE;
    }

    /**
     * Checks if a book is available to borrow
     *
     * @return whether the book is available
     */
    @Override
    public boolean isAvailable() {
        return Issuable.Status.AVAILABLE.equals(this.getStatus());
    }

    /**
     * Displays relevant info about the normal book
     */
    @Override
    public void displayBook() {
        String info = String.format("%s by %s\n\t", getTitle(), getAuthor().getName());

        if (this.getStatus() == Status.BORROWED) {
            info += "UNAVAILABLE. Expected return: " + dueDate.format(dateFormatter);
        } else if (this.getStatus() == Status.PROCESSING) {
            info += "PROCESSING. Available soon.";
        } else {
            info += "AVAILABLE";
        }

        info += "\n\tPages: " + getPages();
        System.out.println(info);
    }
}
