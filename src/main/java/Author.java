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

    @Override
    public int compareTo(Author o) {
        return (name.compareTo(o.name)) * 1000 +
                age - o.age;
    }
}
