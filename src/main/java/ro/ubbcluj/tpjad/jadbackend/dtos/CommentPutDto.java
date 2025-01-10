package ro.ubbcluj.tpjad.jadbackend.dtos;

import lombok.Data;

@Data
public class CommentPutDto {
    private final String content;
    private final String date;
}
