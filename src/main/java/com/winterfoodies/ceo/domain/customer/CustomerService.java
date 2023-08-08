package com.winterfoodies.ceo.domain.customer;

import com.winterfoodies.ceo.aligo.SmsEvent;
import com.winterfoodies.ceo.aligo.SmsService;
import com.winterfoodies.ceo.domain.dashboard.service.DashBoardService;
import com.winterfoodies.ceo.domain.product.repository.ProductRepository;
import com.winterfoodies.ceo.domain.store.repository.StoreRepository;
import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.dto.order.OrderRequestDto;
import com.winterfoodies.ceo.dto.product.ProductRequestDto;
import com.winterfoodies.ceo.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Qualifier(value = "requestUser")
    @Autowired
    private User customer;

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    private final ProductRepository productRepository;

    private final DashBoardService dashBoardService;

    private final ApplicationEventPublisher eventPublisher;



    @Transactional
    public boolean makeOrders(OrderRequestDto orderRequestDto){
        System.out.println("========");
        System.out.println(customer.getId());
        System.out.println("========");
        Optional<Store> storeOptional = storeRepository.findById(orderRequestDto.getStoreId());
        Optional<User> userOptional = userRepository.findById(customer.getId());
        if(storeOptional.isPresent() && !CollectionUtils.isEmpty(orderRequestDto.getProductRequestDtoList())){
            Order order = new Order();
            order.setStore(storeOptional.get());
            order.setProcessYn("N");
            order.setUser(userOptional.get());
            order.setCreateAt(LocalDateTime.now());

            orderRepository.save(order);

            for(ProductRequestDto productRequestDto : orderRequestDto.getProductRequestDtoList()){
                Optional<Product> productOptional = productRepository.findById(productRequestDto.getId());
                if(productOptional.isPresent()){
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(productOptional.get());
                    orderProduct.setQuantity(productRequestDto.getQuantity());
                    orderProduct.setClientMessage(productRequestDto.getClientMessage());
                    orderProduct.setVisitTime(LocalDateTime.now());
                    orderProductRepository.save(orderProduct);
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }


    @Transactional
    public boolean finishOrder(Long orderId){
        //주문 조회
        Order foundOrder = orderRepository.findById(orderId).orElse(null);
        if(foundOrder == null){
            return false;
        }
        Store store = foundOrder.getStore();
        String storeName = store.getStoreDetail().getName();
        foundOrder.setProcessYn("Y");
//        List<OrderProduct> orderProducts = foundOrder.getOrderProducts();
        //고객정보
        User foundUser = foundOrder.getUser();
        //1. 해당 주문 정산
        Sales sales = new Sales();
        sales.setOrderAt(LocalDateTime.now());
        sales.setCustomerId(foundUser.getId());
        Long totalPrice = 0L;
//        for(OrderProduct orderProduct : orderProducts){
//            long quantity  = orderProduct.getQuantity();
//            long money = quantity * orderProduct.getProduct().getPrice();
//            totalPrice += money;
//        }

        sales.setTotalPrice(totalPrice);
        int result = dashBoardService.insertSales(sales);

        //2. 고객에게 문자 알림
        System.out.println("문자 이벤트 생성");
        SmsEvent smsEvent = new SmsEvent();
        smsEvent.setMessage(storeName + "에 주문하신 음식이 완료 되었습니다! 찾으러와주세요!");
        smsEvent.setPhoneNumber("01074998045");
        eventPublisher.publishEvent(smsEvent);

        return result == 1;
    }
}
