import lombok.*;

import java.util.Comparator;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public abstract class Book {
    private String title;
    private Author author;
    private String isbn;
    private int pages;

    public Book(String title, Author author, String isbn, int pages) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pages = pages;
    }

    public abstract void displayBook();

    @AllArgsConstructor
    public static class BookComparator implements Comparator<Book> {
        private String criteria;

        @Override
        public int compare(Book o1, Book o2) {
            if (o1 == null || o2 == null) {
                throw new IllegalArgumentException("books must not be null");
            }

            return switch (criteria) {
                case "title" -> o1.title.compareTo(o2.title);
                case "pages" -> o1.pages - o2.pages;
                case "author" -> o1.author.compareTo(o2.author);
                default -> o1.isbn.compareTo(o2.isbn);
            };
        }
    }
}
