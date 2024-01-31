package management.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {

    @NotBlank(message = "Name can not be null")
    private String name;

    @NotBlank(message = "Surname can not be null")
    private String surname;

}
