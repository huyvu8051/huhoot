package com.huhoot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class Customer extends Auditable implements CustomUserDetails {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String username;

    @Column
    private String fullName;

    private String password;

    private boolean isNonLocked;

    private UUID socketId;

    @OneToMany(mappedBy = "key.customer")
    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Challenge> challenges = new ArrayList<>();

    @OneToMany(mappedBy = "key.customer")
    private List<StudentAnswer> studentAnswers = new ArrayList<>();

    public Customer(String formattedUsername, String fullName, String hashedPassword) {
        this.username = formattedUsername;
        this.password = hashedPassword;
        this.fullName = fullName;
        this.isNonLocked = true;
    }


    public void setNonLocked(boolean isNonLocked) {
        this.isNonLocked = isNonLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("CUSTOMER"));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
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
