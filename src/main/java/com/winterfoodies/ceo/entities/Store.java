package com.winterfoodies.ceo.entities;

import com.winterfoodies.ceo.entities.enums.status.StoreStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "STORE")
@Getter
@Setter
@SequenceGenerator(name = "storeSeq", sequenceName = "STORE_SEQ", initialValue = 1, allocationSize = 1)
public class Store {

    @Column(name = "STORE_ID")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storeSeq")
    private Long id;

    @Column(name = "STORE_STATUS")
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "STORE_DETAIL_ID")
    private StoreDetail storeDetail;
}
