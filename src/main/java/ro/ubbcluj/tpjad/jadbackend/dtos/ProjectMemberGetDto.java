package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class ProjectMemberGetDto {
    private final long userId;
    private final String username;
    private final String role;
}
