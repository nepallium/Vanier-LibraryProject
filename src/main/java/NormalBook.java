import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Getter
@Setter
public class NormalBook extends Book implements Issuable, Comparable<NormalBook> {
    private Student currentBorrower;
    private LocalDate dueDate;
    private int loanPeriodWeeks = 4; // default value
    private static final double DAILY_FEE = 0.5;
    private Issuable.Status status;

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public NormalBook(String title, Author author, String isbn, int pages) {
        super(title, author, isbn, pages);
        this.status = Issuable.Status.AVAILABLE;
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

    @Override
    public int compareTo(NormalBook o) {
        if (!(o instanceof NormalBook other)) {
            return 0;
        }

        // Treat null dueDate as "infinitely far in the future", or simply no due date
        if (this.dueDate == null && other.dueDate == null) {
            return 0;
        }
        if (this.dueDate == null) {
            return 1;
        }
        if (other.dueDate == null) {
            return -1;
        }

        return (this.dueDate.compareTo(other.dueDate)) * 1000 +
                this.getTitle().compareTo(o.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NormalBook that = (NormalBook) o;
        return loanPeriodWeeks == that.loanPeriodWeeks && Objects.equals(currentBorrower, that.currentBorrower) && Objects.equals(dueDate, that.dueDate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dueDate, loanPeriodWeeks, status);
    }
}
