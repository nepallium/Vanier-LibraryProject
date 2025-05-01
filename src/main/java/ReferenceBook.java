import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class ReferenceBook extends Book {
    private String shelfLocation;
    private int totalCopies;

    public ReferenceBook(String isbn, String title, int pages, Author author, int totalCopies, String shelfLocation) {
        super(isbn, title, pages, author);
        this.totalCopies = totalCopies;
        this.shelfLocation = shelfLocation;
    }

    /**
     * Displays relevant info about the reference book
     */
    @Override
    public void displayBook() {
        String info = String.format("%s by %s\n\t" +
                "Shelf location: %s\n\t" +
                "Total copies: %d\n\t" +
                "Pages: %d\n\t" +
                "NOTE: Book is not issuable, put it back place after use",
                getTitle(), getAuthor().getName(), shelfLocation, totalCopies, getPages());

        System.out.println(info);
    }
}
