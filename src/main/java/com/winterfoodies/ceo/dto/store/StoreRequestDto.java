package com.winterfoodies.ceo.dto.store;


import com.winterfoodies.ceo.entities.enums.status.StoreStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class StoreRequestDto {
    private String name;

    private String addressNo;

    private String basicAddress;

    private String detailAddress;

    private String roadCodeNo;

    private String officialCodeNo;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    private String info;

    private String thumbnailImgUrl;

    private StoreStatus status;
}
