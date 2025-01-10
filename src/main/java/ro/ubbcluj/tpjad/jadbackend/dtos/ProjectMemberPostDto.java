package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class ProjectMemberPostDto {
    private final String username;
    private final String role;
}
