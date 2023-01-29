package com.winterfoodies.ceo.entities;

import com.winterfoodies.ceo.entities.enums.status.UserStatus;
import com.winterfoodies.ceo.entities.enums.user.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USERS", indexes = {
        @Index(name = "USER_IDX", columnList = "USER_EMAIL", unique = true)
})
@Getter
@Setter
@SequenceGenerator(name = "userSeq", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
public class User {
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

    @OneToOne(mappedBy = "user")
    private Store store;
}
