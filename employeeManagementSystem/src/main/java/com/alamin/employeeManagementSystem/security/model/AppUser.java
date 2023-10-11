package com.alamin.employeeManagementSystem.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_app_user")
public class AppUser implements UserDetails {
        @SequenceGenerator(
                name = "app_user_sequence",
                sequenceName = "app_user_sequence",
                allocationSize = 1
        )
        @Id
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "app_user_sequence"
        )
        @Column(name = "id")
        private Long id;
        @Column(name = "uuid")
        private UUID uuid;
        private String firstname;
        private String lastname;

        @Column(name = "username")
        private String username;

        @Column(unique = true, name = "email")
        private String email;

        @Column(name = "password")
        private String password;

        @Column(name = "created_at")
        private Date createdAt;

        @Column(name = "updated_at")
        private Date updatedAt;
        @Column(name = "deleted_at")
        private Date deletedAt;

        @Enumerated(EnumType.STRING)
        private Role role;
        @OneToMany(mappedBy = "user")
        private List<Token> tokens;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
            return role.getAuthorities();
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return email;
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

