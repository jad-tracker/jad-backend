package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class ProjectGetDto {
    private final long id;
    private final String name;
    private final String description;
    private final String lead;
}
