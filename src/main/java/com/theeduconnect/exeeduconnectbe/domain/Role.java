package com.theeduconnect.exeeduconnectbe.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Set<User> users = new LinkedHashSet<>();
}
