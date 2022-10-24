package com.github.damian_git_99.backend.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.damian_git_99.backend.project.Project;
import com.github.damian_git_99.backend.user.role.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "roles_id"})})
    @JsonIgnore
    @ToString.Exclude
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @Builder.Default
    private List<Project> projects = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addProject(Project project){
        project.setUser(this);
        projects.add(project);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
