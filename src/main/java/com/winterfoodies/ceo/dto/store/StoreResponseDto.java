package com.winterfoodies.ceo.dto.store;


import com.winterfoodies.ceo.entities.StoreDetail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class StoreResponseDto {
    private String message;
    private String redirect;
    private String result;
    private Long id;
    private String name;
    private String addressNo;
    private String basicAddress;
    private String detailAddress;
    private String info;
    private String roadCodeNo;

    private String officialCodeNo;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    public void fllWithStoreDetail(StoreDetail storeDetail){
        this.name = storeDetail.getName();
        this.addressNo = storeDetail.getAddressNo();
        this.basicAddress = storeDetail.getBasicAddress();
        this.detailAddress = storeDetail.getDetailAddress();
        this.info = storeDetail.getInfo();
        this.openTime = storeDetail.getOpenTime();
        this.closeTime = storeDetail.getCloseTime();
        this.roadCodeNo = storeDetail.getRoadCodeNo();
        this.officialCodeNo =storeDetail.getOfficialCodeNo();
    }
}
