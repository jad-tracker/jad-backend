package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class UserLoginPostDto {
    private final String username;
    private final String password;
}