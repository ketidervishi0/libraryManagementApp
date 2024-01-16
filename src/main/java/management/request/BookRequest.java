package management.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class BookRequest {

    @NotBlank(message = "Title can not be null")
    private String title;

    @NotBlank(message = "Description can not be null")
    private String description;

    @NotBlank(message = "Publication_date can not be null")
    private String publicationDate;

    @NotNull(message = "Author ID can not be null")
    private Long authorId;

    @NotNull(message = "Publisher ID can not be null")
    private Long publisherId;

}
