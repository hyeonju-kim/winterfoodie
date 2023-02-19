package com.winterfoodies.ceo.aligo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsEvent {
    private String message;
    private String phoneNumber;
}
