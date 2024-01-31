package management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;
    private String publicationDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonBackReference
    private Publisher publisher;
}
