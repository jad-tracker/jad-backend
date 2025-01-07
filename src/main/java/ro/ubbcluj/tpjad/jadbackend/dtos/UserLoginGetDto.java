package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class UserLoginGetDto {
    private final String token;
    private final long expiresIn;
}
