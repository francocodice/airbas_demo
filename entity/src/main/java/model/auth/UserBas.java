package model.auth;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "userbas")
public class UserBas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userbas_authorities",
            joinColumns = {@JoinColumn(name = "userbas_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authorities_id", referencedColumnName = "id")})
    //Set<Authority> roles = new HashSet<>();
    private List<Authority> authorities;



    //@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    //Set<Authority> roles = new HashSet<>();

    /*@OneToOne(mappedBy = "userbas", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserBasDetail userbasdetail;*/

}
