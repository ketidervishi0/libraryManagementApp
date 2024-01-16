package management.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class AuthorRequest {

    private Long authorId;

    @NotBlank(message = "Name can not be null")
    private String name;

    @NotBlank(message = "Surname can not be null")
    private String surname;

}
