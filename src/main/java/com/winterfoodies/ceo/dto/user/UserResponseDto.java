package com.winterfoodies.ceo.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class UserResponseDto {
    private String message;
    private String redirect;
    private String result;
    @JsonIgnore
    private HttpStatus status;

    public static UserResponseDto empty(){
        return builder()
                .message("empty")
                .build();
    }

}
