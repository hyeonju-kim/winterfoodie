package com.winterfoodies.ceo.dto.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;



@Getter
@Setter
public class TempAuthInfo{
    @Id
    private String email;
    private String tempPassword;
}
