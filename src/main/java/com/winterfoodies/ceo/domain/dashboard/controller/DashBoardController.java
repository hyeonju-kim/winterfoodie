package com.winterfoodies.ceo.domain.dashboard.controller;


import com.winterfoodies.ceo.domain.dashboard.service.DashBoardService;
import com.winterfoodies.ceo.domain.order.OrderService;
import com.winterfoodies.ceo.domain.store.StoreService;
import com.winterfoodies.ceo.dto.dashboard.DashBoard;
import com.winterfoodies.ceo.dto.order.OrderResponseDto;
import com.winterfoodies.ceo.dto.store.StoreResponseDto;
import com.winterfoodies.ceo.dto.user.UserResponseDto;
import com.winterfoodies.ceo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/view/dashboard")
@RequiredArgsConstructor
@Controller
public class DashBoardController {

    @Qualifier("requestUser")
    @Autowired
    private User requestUser;

    private final StoreService storeService;

    private final DashBoardService dashBoardService;

    private final OrderService orderService;

    @GetMapping(path = {"", "/main"})
    public String dashBoardMain(Model model){

        //TODO 대시보드 통계 값 개발

        //1. 유저 정보
        model.addAttribute("user", UserResponseDto.of(requestUser));

        //2. 가게 정보
        StoreResponseDto store = storeService.retrieveStoreWithoutException(requestUser);
        model.addAttribute("store", store);

        //3. 대시보드 정보
        DashBoard dashBoard = dashBoardService.retrieveDashBoard(store);
        model.addAttribute("dashBoard",dashBoard);

        return "main";
    }

    @GetMapping(path = "/table")
    public String table(Model model){
        //1. 유저 정보
        model.addAttribute("user", UserResponseDto.of(requestUser));

        //2. 가게 정보
        StoreResponseDto store = storeService.retrieveStoreWithoutException(requestUser);
        model.addAttribute("store", store);

        //3. 대시보드 정보
        DashBoard dashBoard = dashBoardService.retrieveDashBoard(store);
        model.addAttribute("dashBoard",dashBoard);

        //4. 주문 정보
        List<OrderResponseDto> orderResponseDtos = orderService.retriveAllOrders();
        System.out.println(orderResponseDtos.size());
        model.addAttribute("orders", orderResponseDtos);

        return "tables";
    }
}
