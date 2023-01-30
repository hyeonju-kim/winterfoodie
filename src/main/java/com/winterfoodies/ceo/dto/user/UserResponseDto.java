package com.winterfoodies.ceo.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winterfoodies.ceo.entities.User;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@ToString
public class UserResponseDto{
    private String message;
    private String redirect;
    private String result;
    private Long id;
    private String email;
    private String name;

    @JsonIgnore
    private HttpStatus status;

    public static UserResponseDto empty(){
        return builder()
                .build();
    }

    public static UserResponseDto of(User user){
        return UserResponseDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static UserResponseDto of(User user, HttpStatus httpStatus){
        return UserResponseDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .status(httpStatus)
                .build();
    }

    public static UserResponseDto of(User user, HttpStatus httpStatus, String redirect){
        return UserResponseDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .status(httpStatus)
                .redirect(redirect)
                .build();
    }

    public static UserResponseDto of(HttpStatus httpStatus, String redirect){
        return UserResponseDto
                .builder()
                .status(httpStatus)
                .redirect(redirect)
                .build();
    }

}
