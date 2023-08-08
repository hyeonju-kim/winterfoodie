package com.winterfoodies.ceo.domain.customer;

import com.winterfoodies.ceo.domain.dashboard.service.DashBoardService;
import com.winterfoodies.ceo.dto.order.OrderRequestDto;
import com.winterfoodies.ceo.entities.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final DashBoardService dashBoardService;

    @PostMapping("")
    public Map<String,String> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        System.out.println(orderRequestDto);
        boolean result = customerService.makeOrders(orderRequestDto);
        if(result){
            return Collections.singletonMap("result", "Y");
        }
        return Collections.singletonMap("result", "N");
    }

    @PostMapping("/finish")
    public Map<String, String> finishOrder(@RequestBody OrderRequestDto orderRequestDto){
        Long orderId = orderRequestDto.getOrderId();
        boolean result = customerService.finishOrder(orderId);
        if(result){
            return Collections.singletonMap("result", "Y");
        }else{
            return Collections.singletonMap("result", "N");
        }
    }
}
