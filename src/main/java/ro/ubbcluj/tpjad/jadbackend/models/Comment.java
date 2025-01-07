package ro.ubbcluj.tpjad.jadbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "id_user_comment_fk"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_issue", foreignKey = @ForeignKey(name = "id_issue_comment_fk"))
    private Issue issue;
}
