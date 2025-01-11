package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class CommentGetDto {
    private final Long id;
    private final String content;
    private final String date;
    private final String username;
}
