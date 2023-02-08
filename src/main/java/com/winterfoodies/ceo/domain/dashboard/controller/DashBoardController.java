package com.winterfoodies.ceo.domain.dashboard.controller;


import com.winterfoodies.ceo.domain.user.service.security.UserDetailsImpl;
import com.winterfoodies.ceo.dto.DashBoard;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/view/dashboard")
@Controller
public class DashBoardController {

    @GetMapping(path = {"", "/main"})
    public String dashBoardMain(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        //TODO 대시보드 통계 값 개발
        DashBoard dashBoard = new DashBoard();
        dashBoard.setAccumulatedOrderCount(1875L);

        //1. 유저 정보
        model.addAttribute("user", userDetails.getUser());

        //2. 대시보드 정보
        model.addAttribute("dashboard", dashBoard);
        return "main";
    }
}
