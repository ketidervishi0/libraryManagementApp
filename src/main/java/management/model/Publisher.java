package management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "publisher")
@Data

public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int year;
    private String publisherName;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Book> books;

}
