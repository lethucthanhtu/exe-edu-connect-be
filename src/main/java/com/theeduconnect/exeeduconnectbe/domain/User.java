package com.theeduconnect.exeeduconnectbe.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.theeduconnect.exeeduconnectbe.constants.authentication.provider.ProviderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
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

    @Column(name = "resetpasswordtoken")
    private String resetPasswordToken;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ProviderEnum provider;

    @NotNull @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "roleid", nullable = false)
    @JsonBackReference
    private Role role;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Student student;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Teacher teacher;

    @OneToMany(mappedBy = "userid")
    private Set<Transaction> transactions = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = this.getRole().getRolename();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roleName));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
