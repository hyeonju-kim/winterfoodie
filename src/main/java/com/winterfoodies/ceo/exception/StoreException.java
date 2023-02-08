package com.winterfoodies.ceo.exception;

import com.winterfoodies.ceo.dto.store.StoreResponseDto;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreException extends BasicException{
    private String message;
    private HttpStatus status;
    private StoreResponseDto userResponseDto;
}
