package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class IssuePostDto {
    private final String summary;
    private final String description;
    private final String type;
    private final String status;
    private final String date;
    private final String assignee;
}
