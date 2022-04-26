package project.educatum.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "nastavnici", schema = "project")
public class Teacher implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nastavnik", nullable = false)
    private Integer id;

    @Column(name = "ime", nullable = false, length = 50)
    private String name;

    @Column(name = "prezime", nullable = false, length = 50)
    private String surname;

    @Column(name = "opis", nullable = false, length = 500)
    private String description;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "telefonski_broj", length = 15)
    private String telephoneNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin idAdmin;

    @Column(name = "enabled")
    private Boolean enabled;


    public Teacher() {
    }

    public Teacher(String name, String surname, String description, String email, String password, String telephoneNumber) {
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.email = email;
        this.password = password;
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public String getFullName() {
        return name + ' ' + surname;
    }
}