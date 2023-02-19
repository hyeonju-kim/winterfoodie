package com.winterfoodies.ceo.domain.order;


import com.winterfoodies.ceo.domain.store.repository.StoreRepository;
import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.domain.user.service.UserService;
import com.winterfoodies.ceo.dto.order.OrderRequestDto;
import com.winterfoodies.ceo.dto.order.OrderResponseDto;
import com.winterfoodies.ceo.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Qualifier(value = "requestUser")
    @Autowired
    private User requestUser;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<OrderResponseDto> retriveAllOrders(){
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        User nowUser = userRepository.findById(requestUser.getId()).orElse(new User());
        List<Order> orders = nowUser.getStore().getOrders();

        for(Order order : orders){
            User customer = order.getUser();
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            List<OrderProduct> orderProducts = order.getOrderProducts();
            orderResponseDto.setOrderId(order.getId());
            orderResponseDto.setCustomerId(customer.getId());
            orderResponseDto.setCustomerName(customer.getName());
            StringBuilder orderMenuBuilder = new StringBuilder();
            StringBuilder messageBuilder = new StringBuilder();
            Long totalPrice = 0L;
            Long totalQuantity = 0L;
            for(OrderProduct orderProduct : orderProducts){
               Product nowProduct = orderProduct.getProduct();
               String productName = nowProduct.getName();
               Long productPrice = nowProduct.getPrice();
               totalPrice += productPrice * orderProduct.getQuantity();
               totalQuantity += orderProduct.getQuantity();
               orderMenuBuilder.append(" " + productName + "/" + orderProduct.getQuantity());
                messageBuilder.append(" " + productName + ":" + orderProduct.getClientMessage());
            }

            orderMenuBuilder.append(" 총 금액 : " + totalPrice);
            orderMenuBuilder.append(" 총 수량 : " + totalQuantity);
            orderResponseDto.setOrderMenu(orderMenuBuilder.toString());
            orderResponseDto.setCustomerMessage(messageBuilder.toString());
            orderResponseDto.setOrderDate(LocalDateTime.now().toString()); //TODO 바꿔야 함 MOCK DATA
            orderResponseDto.setProcessYn(order.getProcessYn());
            orderResponseDtos.add(orderResponseDto);

        }
        return orderResponseDtos;
    }
    @Transactional(readOnly = true)
    public List<OrderResponseDto> retrieveAllFinishedOrders(){
        return null;
    }
    @Transactional(readOnly = true)
    public List<OrderResponseDto> retrieveAllUnFinishedOrders(){
        return null;
    }

    @Transactional
    public boolean processOrders(OrderRequestDto orderRequestDto){
        Optional<Order> foundOrder = orderRepository.findById(orderRequestDto.getOrderId());
        if(foundOrder.isPresent()){
            Order targetOrder = foundOrder.get();
            targetOrder.setProcessYn("Y");
            //TODO 고객에게 문자 발송
            return true;
        }
        return false;
    }
}
