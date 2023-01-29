package com.winterfoodies.ceo.domain.user;

import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.dto.user.UserRequestDto;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import com.winterfoodies.ceo.entities.User;
import com.winterfoodies.ceo.entities.enums.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto register(UserRequestDto userRequestDto){
        Optional<User> userOptional = userRepository.findByEmail(userRequestDto.getEmail());
        if(userOptional.isPresent()){
            return UserResponseDto.builder()
                    .message("이미 가입되어 있는 이메일입니다.")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        User user = new User();
        user.setRole(UserType.CEO);
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());

        User savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .message("등록 완료")
                .redirect("/view/user/login")
                .status(HttpStatus.OK)
                .build();
    }



}
