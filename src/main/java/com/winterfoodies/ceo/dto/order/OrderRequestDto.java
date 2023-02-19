package com.winterfoodies.ceo.dto.order;

import com.winterfoodies.ceo.dto.product.ProductRequestDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString

public class OrderRequestDto {
    private Long orderId;
    private List<ProductRequestDto> productRequestDtoList;
    private Long storeId;

}
