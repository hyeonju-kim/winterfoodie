package com.winterfoodies.ceo.dto.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRequestDto {
    private Long id;
    private Long quantity;
    private String clientMessage;
}
