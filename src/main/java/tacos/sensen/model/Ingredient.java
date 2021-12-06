package tacos.sensen.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {
    @Id
    private String id;
    private String name;
    private Type type;

    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
