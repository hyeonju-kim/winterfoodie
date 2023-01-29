package com.winterfoodies.ceo.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;


@Entity
@Table(name = "STORE_DETAIL")
@Getter
@Setter
@SequenceGenerator(name = "storeDetailSeq", sequenceName = "STORE_DETAIL_SEQ", initialValue = 1, allocationSize = 1)
public class StoreDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storeDetailSeq")
    @Column(name = "STORE_DETAIL_ID")
    private Long id;
    /**
     * varchar STORE_NAME "가게이름"
     * 			varchar STORE_ADRESS_NO "가게주소번호"
     * 			varchar STORE_BASIC_ADDRESS "가게기본주소"
     * 			varchar STORE_DETAIL_ADDRESS "가게세부주소"
     * 			varchar STORE_ROAD_CODE "가게도로명주소코드"
     * 		  varchar STORE_OFFICIAL_CODE "가게행정부주소코드"
     * 			varchar STORE_OPEN_TIME "가게 오픈 시간"
     * 			varchar STORE_CLSE_TIME "가게 종료 시간"
     * 			blob STORE_INTRO "가게간단소개"
     * 			varchar STORE_THUMBNAIL_IMG "가게썸네일이미지"
     */
    @Column(name = "STORE_NAME")
    private String name;

    @Column(name = "STORE_ADDRESS_NO")
    private String addressNo;

    @Column(name = "STORE_BASIC_ADDRESS")
    private String basicAddress;

    @Column(name = "STORE_DETAIL_ADDRESS")
    private String detailAddress;

    @Column(name = "STORE_ROAD_CODE_NO")
    private String roadCodeNo;

    @Column(name = "STORE_OFFICIAL_CODE_NO")
    private String officialCodeNo;

    @Column(name = "STORE_OPEN_TIME")
    private LocalDateTime openTime;

    @Column(name = "STORE_CLOSE_TIME")
    private LocalDateTime closeTime;

    @Lob
    @Column(name = "STORE_INFO")
    private String info;

    @Column(name = "STORE_THUMBNAIL_IMGURL")
    private String thumbnailImgUrl;

    @OneToOne(mappedBy = "storeDetail")
    private Store store;
}
