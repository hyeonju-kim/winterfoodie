package com.winterfoodies.ceo;

import com.winterfoodies.ceo.domain.product.repository.ProductRepository;
import com.winterfoodies.ceo.domain.store.repository.StoreRepository;
import com.winterfoodies.ceo.domain.user.repository.UserRepository;
import com.winterfoodies.ceo.entities.*;
import com.winterfoodies.ceo.entities.enums.status.StoreStatus;
import com.winterfoodies.ceo.entities.enums.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final StoreProductRepository storeProductRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        testScenario();
    }

    public void testScenario(){
        //사장 - 오주영
        User ceo = new User();
        ceo.setEmail("blessdutch@naver.com");
        ceo.setPassword(passwordEncoder.encode("100825asa!"));
        ceo.setRole(UserType.CEO);
        ceo.setName("오주영");

        //가게 생성
        Store store = new Store();
        store.setStatus(StoreStatus.OPEN);

        //가게 디테일 생성
        StoreDetail storeDetail = new StoreDetail();
        storeDetail.setName("주영네 붕어빵 가게");
        storeDetail.setBasicAddress("경기도 부천시 소사로 78번길");
        storeDetail.setDetailAddress("두산아파트 101동 1504호");
        storeDetail.setAddressNo("14774");
        storeDetail.setOfficialCodeNo("1111111111");
        storeDetail.setRoadCodeNo("222222222");
        storeDetail.setInfo("존맛탱인 저희가게 많이 놀러와주세요~!");

        //가게에게 가게 디테일 인젝션
        store.setStoreDetail(storeDetail);
        storeRepository.save(store);

        //사장님에게 가게 인젝션
        ceo.setStore(store);
        userRepository.save(ceo);

        //상품 생성
        Product product = new Product();
        product.setName("붕어빵");

        productRepository.save(product);

        //가게에 상품 생성 후 연결
        StoreProduct storeProduct = new StoreProduct();
        storeProduct.setStore(store);
        storeProduct.setProduct(product);
        storeProductRepository.save(storeProduct);

        //사용자 - 장준혁
        User customer = new User();
        customer.setRole(UserType.CUSTOMER);
        customer.setPassword("1216132");
        customer.setEmail("ss@kbanknow.com");
        customer.setName("장준혁");
        userRepository.save(customer);


        //주문
        Order order = new Order();
        order.setUser(customer);
        order.setStore(ceo.getStore());
        orderRepository.save(order);

        //주문상세
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setClientMessage("빨리 만들어주세요");
        orderProduct.setQuantity(3L);
        orderProduct.setVisitTime(LocalDateTime.now().plus(Duration.ofHours(1)));
        orderProductRepository.save(orderProduct);

    }
}