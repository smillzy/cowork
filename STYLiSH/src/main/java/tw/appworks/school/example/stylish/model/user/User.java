package tw.appworks.school.example.stylish.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "user",
        indexes = {@Index(name = "access_token", columnList = "access_token"),
                @Index(name = "role_id", columnList = "role_id"),
                @Index(name = "user", columnList = "provider, email, password")})
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "provider", length = 15, nullable = false)
    private String provider;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name", length = 127, nullable = false)
    private String name;

    @Column(name = "picture", length = 500)
    private String picture;

    @Column(name = "access_token", length = 1000, nullable = false, columnDefinition = "varchar(1000) default ''")
    private String accessToken;

    @Column(name = "access_expired", nullable = false)
    private Long accessExpired;

    @Column(name = "login_at")
    private Timestamp loginAt;

    @Override
    public String toString() {
        return "User [id=" + id + ", role=" + role + ", provider=" + provider + ", email=" + email + ", password="
                + password + ", name=" + name + ", picture=" + picture + ", accessToken=" + accessToken
                + ", accessExpired=" + accessExpired + ", loginAt=" + loginAt + "]";
    }

}
