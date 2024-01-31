package management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "review")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String reviewText;

    private int rating;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;
}
