import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ReferenceBook extends Book {
    private String shelfLocation;
    private int totalCopies;

    public ReferenceBook(String title, Author author, String isbn, int pages, String shelfLocation, int totalCopies) {
        super(title, author, isbn, pages);
        this.shelfLocation = shelfLocation;
        this.totalCopies = totalCopies;
    }

    /**
     * Displays relevant info about the reference book
     */
    @Override
    public void displayBook() {
        String info = String.format("%s by %s:\n\t" +
                        "Shelf location: %s\n\t" +
                        "Total copies: %d\n\t" +
                        "Pages: %d\n\t" +
                        "NOTE: Book is not issuable, put it back in place after use",
                getTitle(), getAuthor().getName(), shelfLocation, totalCopies, getPages());

        System.out.println(info);
    }
}
