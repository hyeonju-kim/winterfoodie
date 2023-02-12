package com.winterfoodies.ceo.domain.dashboard.service;


import com.winterfoodies.ceo.config.properties.UiControlProperties;
import com.winterfoodies.ceo.dto.dashboard.DashBoard;
import com.winterfoodies.ceo.dto.dashboard.Message;
import com.winterfoodies.ceo.dto.dashboard.TopInfoCard;
import com.winterfoodies.ceo.dto.store.StoreResponseDto;
import lombok.RequiredArgsConstructor;
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

     private void settingTopCardInfo(DashBoard dashBoard){
         //TODO 통계데이터 세팅
        String thisMonth = String.format("%d월", LocalDateTime.now().getMonthValue());
        String yesterDay = String.format(LocalDateTime.now().minus(Period.ofDays(1)).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        String thisYear = String.format("%d년", LocalDateTime.now().getYear());

        List<TopInfoCard> topInfoCards = new ArrayList<>();
        topInfoCards.add(new TopInfoCard("이달의 매출(" + thisMonth + ")", "45,000원"));
        topInfoCards.add(new TopInfoCard("올해의 누적 매출(" + thisYear + ")", "1,145,000원"));
        topInfoCards.add(new TopInfoCard("어제의 매출(" + yesterDay + ")", "325,000원"));
        topInfoCards.add(new TopInfoCard("총 누적 주문건 수", "1,232개"));

        dashBoard.setTopInfoCards(topInfoCards);

        return;



     }
}
