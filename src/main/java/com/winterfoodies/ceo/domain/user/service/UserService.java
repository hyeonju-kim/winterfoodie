package com.winterfoodies.ceo.domain.user.service;

import com.winterfoodies.ceo.config.properties.UiControlProperties;
import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.dto.user.UserRequestDto;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import com.winterfoodies.ceo.entities.User;
import com.winterfoodies.ceo.entities.enums.user.UserType;
import com.winterfoodies.ceo.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UiControlProperties uiControlProperties;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto register(UserRequestDto userRequestDto){
        Optional<User> userOptional = userRepository.findByEmail(userRequestDto.getEmail());
        if(userOptional.isPresent()){
            throw new UserException("해당 이메일이 이미 존재합니다.", HttpStatus.BAD_REQUEST, null);
        }

        User newUser = new User(userRequestDto, UserType.CEO);
        newUser.encryptPassword(passwordEncoder);

        User savedUser = userRepository.save(newUser);

        return UserResponseDto.builder()
                .message("등록 완료")
                .redirect(uiControlProperties.getRedirectLogin())
                .status(HttpStatus.OK)
                .build();
    }

    @Transactional
    public UserResponseDto login(UserRequestDto userRequestDto){
        String email = userRequestDto.getEmail();
        String pw = userRequestDto.getPassword();
        User retrievedUser = userRepository.findByEmail(email).orElseThrow(()->{
            return new UserException("가입되지 않은 이메일입니다.", HttpStatus.BAD_REQUEST, null);
        });
        //조회한 비밀번호
        String foundPw = retrievedUser.getPassword();

        //비밀번호 같은지 여부 파악
        if(!pw.equals(foundPw)){
            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, null);
        }

        return UserResponseDto.of(retrievedUser, HttpStatus.OK, uiControlProperties.getRedirectDashboard());
    }

    @Transactional(readOnly = true)
    public boolean isExistEmail(UserRequestDto userRequestDto){
        String email = userRequestDto.getEmail();
        User foundUser = userRepository.findByEmail(email).orElseThrow(
                ()->new UserException("가입되지 않은 이메일입니다.", HttpStatus.BAD_REQUEST, null)
        );
        return true;
    }

}
