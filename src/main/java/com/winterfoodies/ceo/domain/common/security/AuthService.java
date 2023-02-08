package com.winterfoodies.ceo.domain.common.security;


import com.winterfoodies.ceo.domain.common.EmailService;
import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.dto.common.TempAuthInfo;
import com.winterfoodies.ceo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private String instancePasswordGenerator(){
        Random random = new Random(122929L);
        int k = 1;
        for(int i = 0; i < 7; i++) {
            int no = random.nextInt(10);
            k = k * 10 + no;
        }

        return String.valueOf(k);
    }

    @Transactional
    public boolean saveTempAuthInfo(String email){
        //임시 비밀번호 생성
        String tempPassword = instancePasswordGenerator();

        //임시 유저정보 생성
        TempAuthInfo tempAuthInfo = new TempAuthInfo();
        tempAuthInfo.setEmail(email);
        tempAuthInfo.setTempPassword(tempPassword);

        //임시 유저비밀번호로 setting
        User user = userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(tempPassword));

        //email 발송 이벤트 퍼블리싱(비동기)
        eventPublisher.publishEvent(tempAuthInfo);
        return true;
    }


}
