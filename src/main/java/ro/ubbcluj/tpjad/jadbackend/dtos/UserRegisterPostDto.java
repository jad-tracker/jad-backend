package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class UserRegisterPostDto {
    private final String username;
    private final String password;
    private final String confirmPassword;
}