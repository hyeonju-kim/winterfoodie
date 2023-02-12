package com.winterfoodies.ceo.domain.store;

import com.winterfoodies.ceo.domain.store.repository.StoreRepository;
import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.domain.user.service.security.UserDetailsImpl;
import com.winterfoodies.ceo.dto.common.ResponseBox;
import com.winterfoodies.ceo.dto.store.StoreRequestDto;
import com.winterfoodies.ceo.dto.store.StoreResponseDto;
import com.winterfoodies.ceo.entities.Store;
import com.winterfoodies.ceo.entities.StoreDetail;
import com.winterfoodies.ceo.entities.User;
import com.winterfoodies.ceo.exception.StoreException;
import com.winterfoodies.ceo.exception.UserException;
import io.netty.util.internal.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseBox register(StoreRequestDto storeRequestDto, User requestUser){

        long userId = requestUser.getId();
        User retrievedUser = userRepository.findById(userId).orElseThrow(()->new UserException("올바르지 않은 접근입니다.", HttpStatus.BAD_REQUEST, null));

        StoreDetail storeDetail = new StoreDetail();
        storeDetail.fillValue(storeRequestDto);


        Store store = new Store();
        store.changeStoreDetail(storeDetail);

        retrievedUser.setStore(store);

        storeRepository.save(store);
        ResponseBox responseBox = new ResponseBox();
        responseBox.redirect("/view/dashboard");
        responseBox.status(HttpStatus.OK);
        return responseBox;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseBox modify(StoreRequestDto storeRequestDto, User requestUser){
        long userId = requestUser.getId();
        User retrievedUser = userRepository.findById(userId).orElseThrow(()->new UserException("올바르지 않은 접근입니다.", HttpStatus.BAD_REQUEST, null));
        Store store = retrievedUser.getStore();
        StoreDetail storeDetail = store.getStoreDetail();
        storeDetail.fillValue(storeRequestDto);
        ResponseBox responseBox = new ResponseBox();
        responseBox.redirect("/view/dashboard");
        responseBox.status(HttpStatus.OK);
        return responseBox;
    }

    @Transactional(readOnly = true)
    public StoreResponseDto retrieveStore(User requestUser){
        long userId = requestUser.getId();
        User retrievedUser = userRepository.findById(userId).orElseThrow(()->new UserException("올바르지 않은 접근입니다.", HttpStatus.BAD_REQUEST, null));
        Store store = retrievedUser.getStore();
        if(Objects.isNull(store)){
            throw new StoreException("수정할 가게 정보가 없습니다. 가게를 먼저 등록해주세요.", HttpStatus.UNAUTHORIZED, null);
        }
        StoreResponseDto storeResponseDto = new StoreResponseDto();
        storeResponseDto.fllWithStoreDetail(store.getStoreDetail());
        return storeResponseDto;
    }

    @Transactional(readOnly = true)
    public StoreResponseDto retrieveStoreWithoutException(User requestUser){
        long userId = requestUser.getId();
        User retrievedUser = new User();
        try {
            retrievedUser = userRepository.findById(userId).orElseThrow(() -> new UserException("올바르지 않은 접근입니다.", HttpStatus.BAD_REQUEST, null));
        }catch (Exception e){
            log.debug("올바르지 않은 접근임");
            return StoreResponseDto.empty();
        }
        Store store = retrievedUser.getStore();
        if(Objects.isNull(store)){
           return StoreResponseDto.empty();
        }
        StoreResponseDto storeResponseDto = new StoreResponseDto();
        storeResponseDto.fllWithStoreDetail(store.getStoreDetail());
        return storeResponseDto;
    }


}
