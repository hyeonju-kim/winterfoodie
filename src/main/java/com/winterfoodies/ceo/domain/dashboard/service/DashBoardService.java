package com.winterfoodies.ceo.domain.dashboard.service;


import com.winterfoodies.ceo.config.properties.UiControlProperties;
import com.winterfoodies.ceo.dto.dashboard.DashBoard;
import com.winterfoodies.ceo.dto.dashboard.Message;
import com.winterfoodies.ceo.dto.dashboard.TopInfoCard;
import com.winterfoodies.ceo.dto.store.StoreResponseDto;
import com.winterfoodies.ceo.entities.Sales;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DashBoardService {
    private final JdbcTemplate jdbcTemplate;

    private final UiControlProperties uiControlProperties;
     public DashBoard retrieveDashBoard(StoreResponseDto storeResponseDto){
         LocalDateTime now = LocalDateTime.now();
         now.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
         DashBoard dashBoard = new DashBoard();
         //1.매장 대시보드 세팅
         settingTopCardInfo(dashBoard);

         //2.매장 알람 메시지 세팅
            //1) 우선적으로 가게등록 메시지를 세팅해야 하는 지 확인해줍니다.
            if(Objects.isNull(storeResponseDto) || "N".equals(storeResponseDto.getHasStoreYn())){
                dashBoard.setAlertMessages(Collections.singletonList(
                        Message.builder()
                                .contents("원활한 사용을 위해<br> 우선적으로 가게 정보를 등록해주세요!")
                                .link(uiControlProperties.getRedirectStoreRegister())
                                .date(now.toString()).build()
                ));
            }
        return dashBoard;
     }

     public void settingTopCardInfo(DashBoard dashBoard){
         String thisMonthTotal = convertLongToMoneyFormat(getAllSalesByMonth());
         String thisYearTotal = convertLongToMoneyFormat(getAllSalesByYear());
         String yesterDayTotal = convertLongToMoneyFormat(getYesterDaySales());
         String total = convertLongToMoneyFormat(getTotalOrdersCount());

         //TODO 통계데이터 세팅
        String thisMonth = String.format("%d월", LocalDateTime.now().getMonthValue());
        String yesterDay = String.format(LocalDateTime.now().minus(Period.ofDays(1)).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        String thisYear = String.format("%d년", LocalDateTime.now().getYear());

        List<TopInfoCard> topInfoCards = new ArrayList<>();
        topInfoCards.add(new TopInfoCard("이달의 매출(" + thisMonth + ")", thisMonthTotal + "원"));
        topInfoCards.add(new TopInfoCard("올해의 누적 매출(" + thisYear + ")", thisYearTotal + "원"));
        topInfoCards.add(new TopInfoCard("어제의 매출(" + yesterDay + ")", yesterDayTotal + "원"));
        topInfoCards.add(new TopInfoCard("총 누적 주문건 수", total + "개"));

        dashBoard.setTopInfoCards(topInfoCards);

        return;



     }

     public int insertSales(Sales sales){
         String sql = "insert into sales(customer_id, order_at, product_id, total_price) values(?, ?, ?, ?)";
         int result = jdbcTemplate.update(sql, sales.getCustomerId(), sales.getOrderAt(), sales.getProductId(), sales.getTotalPrice());
         return result;
     }

     //이달의 매출
     public Long getAllSalesByMonth(){
         String sql =  "SELECT sum(total_price) FROM sales total_price " +
                        "WHERE ( ORDER_AT > LAST_DAY(NOW() - interval 1 month) " +
                        "AND ORDER_AT <= LAST_DAY(NOW()))";
         Long result = jdbcTemplate.queryForObject(sql, Long.class);
         if(result == null) result = 0L;
         return result;
     }

     //올해의 누적 매출
     public Long getAllSalesByYear(){
        String sql = "select sum(total_price) from sales where year(order_at) = year(now())";
        Long result = jdbcTemplate.queryForObject(sql, Long.class);
        if(result == null) result = 0L;
        return result;
     }

    //어제의 매출
    public Long getYesterDaySales(){
         String sql = "select sum(total_price) from sales where order_at = curdate() - INTERVAL 1 DAY";
         Long result = jdbcTemplate.queryForObject(sql, Long.class);
         if(result == null) result = 0L;
         return result;
    }

    //총 누적 주문건 수
    public Long getTotalOrdersCount(){
         String sql = "select count(*) from sales";
        Long result = jdbcTemplate.queryForObject(sql, Long.class);
        if(result == null) result = 0L;
        return result;
    }
    //12345


    private static String convertLongToMoneyFormat(Long data){
         String val = String.valueOf(data);
         StringBuilder builder = new StringBuilder();
         int len = val.length();
         int point = 0;
         for(int i = 0; i < len; i++){
             char c = val.charAt(len - (i + 1));
             builder.append(c);
             if(point == 2 && i != len - 1){
                 builder.append(",");
                 point = 0;
             }else{
                 point++;
             }
         }
         String convertedVal = builder.reverse().toString();
         return convertedVal;
    }





}
