package management.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PublisherRequest {

    @Min(value = 1900)
    @Max(value = 2010)
    private int year;

    @NotBlank(message = "Publisher name can not be null")
    private String publisherName;
}


