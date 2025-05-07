import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Author implements Comparable<Author> {
    private String name;
    private int age;
    private Gender gender;

    public Author(String name) {
        this.name = name;
    }

    /**
     * Sorts authors by name, and, if equal, by age, ascending
     *
     * @param o the object to be compared.
     * @return the comparison result
     */
    @Override
    public int compareTo(Author o) {
        return (name.compareTo(o.name)) * 1000 +
                age - o.age;
    }
}
