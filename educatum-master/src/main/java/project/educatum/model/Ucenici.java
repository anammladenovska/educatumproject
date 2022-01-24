package project.educatum.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ucenici", schema = "project")
public class Ucenici implements UserDetails {

    public Ucenici(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ucenik", nullable = false)
    private Integer id;

    @Column(name = "opis", length = 500)
    private String opis;

    @Column(name = "ime", nullable = false, length = 50)
    private String ime;

    @Column(name = "prezime", nullable = false, length = 50)
    private String prezime;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 300)
    private String password;

    @Column(name = "telefonski_broj", length = 15)
    private String telefonskiBroj;

    public Ucenici(String opis, String ime, String prezime, String email, String password, String telefonskiBroj) {
        this.opis = opis;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.telefonskiBroj = telefonskiBroj;
    }

    public String getTelefonskiBroj() {
        return telefonskiBroj;
    }

    public void setTelefonskiBroj(String telefonskiBroj) {
        this.telefonskiBroj = telefonskiBroj;
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
        return null;
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
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}