package com.theeduconnect.exeeduconnectbe.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Size(max = 255)
    @Column(name = "fullname")
    private String fullname;

    @Column(name = "dateofbirth")
    private LocalDate dateofbirth;

    @Size(max = 255)
    @Column(name = "avatarurl")
    private String avatarurl;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 11)
    @Column(name = "phone", length = 11)
    private String phone;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "balance")
    private Double balance;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roleid", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    @OneToMany(mappedBy = "userid")
    private Set<Transaction> transactions = new LinkedHashSet<>();

}