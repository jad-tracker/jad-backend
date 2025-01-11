package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class CommentPostDto {
    private final String content;
    private final String date;
}
