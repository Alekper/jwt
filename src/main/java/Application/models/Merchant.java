package Application.models;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Getter
    @Column(nullable = false)
    private String type;

    @Getter
    @Column(nullable = false)
    private String name;

    @Getter
    @Column(nullable = false)
    private String ownName;

    @Getter
    @Column(nullable = false)
    private String address;

    @Getter
    @Column(nullable = false)
    private String phone;

    @Getter
    @Column(unique = true)
    private String email;

    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @Column(nullable = false)
    private String role = "ROLE_MERCHANT";

    @Getter
    @Column(nullable = false)
    public boolean isActive = false;

    @Getter
    @Column()
    private String token;
    public Merchant(String type,
                    String name, String ownName,
                    String address, String phone, String email,
                    String password) {
        this.type = type;
        this.name = name;
        this.ownName = ownName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = "ROLE_MERCHANT";
        this.isActive = false;
    }

}