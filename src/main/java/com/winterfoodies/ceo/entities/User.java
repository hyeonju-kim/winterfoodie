package com.winterfoodies.ceo.entities;

import com.winterfoodies.ceo.dto.user.UserRequestDto;
import com.winterfoodies.ceo.entities.enums.status.UserStatus;
import com.winterfoodies.ceo.entities.enums.user.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS", indexes = {
        @Index(name = "USER_IDX", columnList = "USER_EMAIL", unique = true)
})
@Getter
@Setter
@SequenceGenerator(name = "userSeq", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
public class User implements Serializable {
    static final long serialVersionUID = -3085157956097560247L;

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE")
    private UserType role;

    @Column(name = "USER_PHONE_NUMBER")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS")
    private UserStatus status;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    @OneToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> order = new ArrayList<>();

    public User(UserRequestDto userRequestDto, UserType type){
        this.name = userRequestDto.getName();
        this.email = userRequestDto.getEmail();
        this.phoneNumber = userRequestDto.getPhoneNumber();
        this.role = type;
        this.password = userRequestDto.getPassword();
    }

    public void encryptPassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}
