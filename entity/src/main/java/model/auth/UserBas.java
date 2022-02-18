package model.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.List;

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

    private List<Authority> authorities;



    //@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    //Set<Authority> roles = new HashSet<>();

    /*@OneToOne(mappedBy = "userbas", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserBasDetail userbasdetail;*/

}
