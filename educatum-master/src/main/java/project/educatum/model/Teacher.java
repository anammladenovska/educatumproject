package project.educatum.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "nastavnici", schema = "project")
public class Teacher implements UserDetails {



   public Teacher(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nastavnik", nullable = false)
    private Integer id;

    @Column(name = "ime", nullable = false, length = 50)
    private String ime;

    @Column(name = "prezime", nullable = false, length = 50)
    private String prezime;

    @Column(name = "opis", nullable = false, length = 500)
    private String opis;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "telefonski_broj", length = 15)
    private String telefonskiBroj;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin idAdmin;

    @Column(name = "enabled")
    private Boolean enabled;


    public Teacher(String ime, String prezime, String opis, String email, String password, String telefonskiBroj) {
        this.enabled=false;
        this.ime = ime;
        this.prezime = prezime;
        this.opis = opis;
        this.email = email;
        this.password = password;
        this.telefonskiBroj = telefonskiBroj;

    }

    public Admin getIdAdmin() {
        return idAdmin;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setIdAdmin(Admin idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getMobileNumber() {
        return telefonskiBroj;
    }

    public void setTelefonskiBroj(String telefonskiBroj) {
        this.telefonskiBroj = telefonskiBroj;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSurname() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getName() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName(){
        return ime + ' ' +  prezime;
    }
}