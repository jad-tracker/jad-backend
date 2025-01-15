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
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private IssueType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "id_project", foreignKey = @ForeignKey(name = "id_project_issue_fk"))
    private Project project;

    @ManyToOne
    @JoinColumn(name = "id_assignee", foreignKey = @ForeignKey(name = "id_assignee_issue_fk"))
    private User assignee;
}
