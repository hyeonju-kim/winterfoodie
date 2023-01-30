package com.winterfoodies.ceo.domain.dashboard.controller;


import com.winterfoodies.ceo.domain.user.service.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/view/dashboard")
@Controller
public class DashBoardController {

    @GetMapping(path = {"", "/main"})
    public String dashBoardMain(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("==================================");
        System.out.println(userDetails.getUserResponseDto());
        System.out.println("==================================");
        return "main";
    }
}
