package com.winterfoodies.ceo.domain.user.controller;

import com.winterfoodies.ceo.config.properties.UiControlProperties;
import com.winterfoodies.ceo.domain.common.security.AuthService;
import com.winterfoodies.ceo.domain.user.service.UserService;
import com.winterfoodies.ceo.domain.user.service.security.UserDetailsImpl;
import com.winterfoodies.ceo.dto.user.UserRequestDto;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import com.winterfoodies.ceo.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/view/user")
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthService authService;
    private final UiControlProperties uiControlProperties;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> createLogin(@RequestBody UserRequestDto userRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(), userRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        UserResponseDto responseDto = principal.getUserResponseDto();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<UserResponseDto> logout(@CookieValue("SESSION") String sessionValue){
        System.out.println("sessionValue : " + sessionValue);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(UserResponseDto.of(HttpStatus.OK, uiControlProperties.getRedirectLogin()));
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createRegister(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto responseDto = userService.register(userRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<UserResponseDto> sendForgotPasswordEmail(@RequestBody UserRequestDto userRequestDto){
        boolean isExistMail = userService.isExistEmail(userRequestDto);
        if(isExistMail){
            authService.saveTempAuthInfo(userRequestDto.getEmail());
        }
        return ResponseEntity.ok(UserResponseDto.empty());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserResponseDto> userExceptionHandler(UserException userException){
        UserResponseDto userResponseDto =
                UserResponseDto.builder().message(userException.getMessage()).build();
        return ResponseEntity.status(userException.getStatus()).body(userResponseDto);
    }
}
