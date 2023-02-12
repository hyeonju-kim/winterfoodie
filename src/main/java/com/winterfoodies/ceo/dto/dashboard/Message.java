package com.winterfoodies.ceo.dto.dashboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Message {
    private String contents;
    private String date;
    private String link;
}
