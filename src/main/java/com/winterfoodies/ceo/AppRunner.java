package com.winterfoodies.ceo;

import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.entities.User;
import com.winterfoodies.ceo.entities.enums.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User();
        user.setEmail("blessdutch@naver.com");
        user.setRole(UserType.CEO);
        user.setName("류현수");
        userRepository.save(user);
    }
}