package com.winterfoodies.ceo.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequestDto {
    private String email;
    private String password;
    private String passwordConfirm;
    private String phoneNumber;
    private String name;
}
