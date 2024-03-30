package tw.appworks.school.example.stylish.model.marketing;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hot")
@Data
@NoArgsConstructor
public class Hot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Override
    public String toString() {
        return "Hot [id=" + id + ", title=" + title + "]";
    }

}
