package tw.appworks.school.example.stylish.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "color")
@Data
@NoArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", length = 10, nullable = false)
    private String code = "";

    @Column(name = "name", length = 10, nullable = false)
    private String name = "";

    @Override
    public String toString() {
        return "Color [id=" + id + ", code=" + code + ", name=" + name + "]";
    }

}
