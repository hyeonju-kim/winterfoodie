package com.winterfoodies.ceo.domain.user.service.security;

import com.winterfoodies.ceo.config.properties.UiControlProperties;
import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.domain.user.service.UserService;
import com.winterfoodies.ceo.entities.User;
import com.winterfoodies.ceo.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    private final UiControlProperties uiControlProperties;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()->{
                    return new UserException("존재하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST, null);
                });


        return new UserDetailsImpl(user, uiControlProperties.getRedirectDashboard());
    }
}
