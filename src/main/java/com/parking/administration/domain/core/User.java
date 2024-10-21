package com.parking.administration.domain.core;

import com.parking.administration.annotations.ValidateCPFAndCNPJ;
import com.parking.administration.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(name = "full_name", nullable = false, length = 140)
    private String fullName;

    @Column(name = "email", nullable = false, length = 70)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ValidateCPFAndCNPJ
    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    private Boolean locked;

    private Boolean enabled;

    @Column(name = "created_at", nullable = false, length = 70)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", length = 70)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicleList;

    @OneToMany(mappedBy = "user")
    private List<ParkingSpace> parkingSpaceList;

   public User(String fullName, String email, String password, String document, String username) {
       this.userRole = null;
       this.fullName = fullName;
       this.email = email;
       this.password = password;
       this.document = document;
       this.username = username;

   }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
