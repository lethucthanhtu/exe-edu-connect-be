package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "rolename", length = 50)
    private String rolename;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();
}
