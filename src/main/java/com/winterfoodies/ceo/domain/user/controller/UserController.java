package com.winterfoodies.ceo.domain.user.controller;

import com.winterfoodies.ceo.domain.user.UserService;
import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.dto.user.UserRequestDto;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/view/user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }



    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createRegister(@RequestBody UserRequestDto userRequestDto){

        UserResponseDto responseDto = userService.register(userRequestDto);

        if(!HttpStatus.OK.equals(responseDto.getStatus())){
            return ResponseEntity.status(responseDto.getStatus())
                    .body(responseDto);
        }
        return ResponseEntity.ok(responseDto);

    }

}
