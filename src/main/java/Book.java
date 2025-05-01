import lombok.*;

import java.util.Comparator;

@EqualsAndHashCode
@Getter
@Setter
abstract class Book {
    private String title;
    private Author author;
    private String isbn;
    private int pages;
    private Issuable.Status status;

    public Book(String title, Author author, String isbn, int pages) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pages = pages;
        this.status = Issuable.Status.AVAILABLE;
    }

    public abstract void displayBook();

    @AllArgsConstructor
    public static class BookComparator implements Comparator<Book> {
        private String criteria;

        @Override
        public int compare(Book o1, Book o2) {
            return switch (criteria) {
                case "title" -> o1.title.compareTo(o2.title);
                case "pages" -> o1.pages - o2.pages;
                case "author" -> o1.author.compareTo(o2.author);
                default -> o1.isbn.compareTo(o2.isbn);
            };
        }
    }
}
