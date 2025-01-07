package ro.ubbcluj.tpjad.jadbackend.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageResponse {
    private final String errorMessage;
    private final String details;
}
