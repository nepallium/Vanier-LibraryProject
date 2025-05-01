import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public abstract class User {
    private String name;
    private Gender gender;
    private int id;

    public User(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }
}
