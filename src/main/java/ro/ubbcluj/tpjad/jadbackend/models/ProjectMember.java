package ro.ubbcluj.tpjad.jadbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_members")
public class ProjectMember {
    @EmbeddedId
    private ProjectMemberKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "id_user_project_member_fk"))
    private User user;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "id_project", foreignKey = @ForeignKey(name = "id_project_project_member_fk"))
    private Project project;

    @Column(name = "role")
    private String role;
}
