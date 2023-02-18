package com.winterfoodies.ceo.domain.store.controller;

import com.winterfoodies.ceo.domain.store.StoreService;
import com.winterfoodies.ceo.dto.common.ResponseBox;
import com.winterfoodies.ceo.dto.store.StoreRequestDto;
import com.winterfoodies.ceo.dto.store.StoreResponseDto;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import com.winterfoodies.ceo.entities.User;
import com.winterfoodies.ceo.exception.ExceptionUtils;
import com.winterfoodies.ceo.exception.StoreException;
import com.winterfoodies.ceo.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/view/store")
@Controller
@RequiredArgsConstructor
public class StoreController {

    @Qualifier(value = "requestUser")
    @Autowired
    private User requestUser;

    private final StoreService storeService;

    @GetMapping("/register")
    public String register(){
        return "store-register";
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<ResponseBox> createRegister(@ModelAttribute StoreRequestDto storeRequestDto){
        ResponseBox responseBox = storeService.register(storeRequestDto, requestUser);
        return ResponseEntity.status(responseBox.getStatus()).body(responseBox);
    }

    @GetMapping("/reset")
    public String reset(Model model){
        StoreResponseDto storeResponseDto = storeService.retrieveStore(requestUser);
        model.addAttribute("store", storeResponseDto);
        return "store-reset";
    }

    @PatchMapping("")
    public ResponseEntity<ResponseBox> modifyStore(@ModelAttribute StoreRequestDto storeRequestDto){
        System.out.println("========PATCH==========");
        System.out.println(storeRequestDto);
        ResponseBox responseBox = storeService.modify(storeRequestDto, requestUser);
        return ResponseEntity.status(responseBox.getStatus()).body(responseBox);
    }

    @ExceptionHandler(StoreException.class)
    public ResponseEntity<ResponseBox> storeExceptionHandler(StoreException storeException, HttpServletRequest request){
        System.out.println(request.getMethod().toString());
        if(ExceptionUtils.isForView(request)){
            //TODO PAGE RETURN EXCEPTION HANDLER 개발
            System.out.println("TODO PAGE RETURN");
        }
        ResponseBox responseBox = new ResponseBox();
        responseBox.status(storeException.getStatus());
        responseBox.message(storeException.getMessage());
        return ResponseEntity.status(responseBox.getStatus()).body(responseBox);
    }
}
