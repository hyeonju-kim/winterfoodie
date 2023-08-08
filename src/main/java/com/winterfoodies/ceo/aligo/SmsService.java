package com.winterfoodies.ceo.aligo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class SmsService{

    @Value("${aligo.apikey}")
    private String key;
    @Value("${aligo.id}")
    private String id;
    @Value("${aligo.sender}")
    private String sender;

    @Async("threadPoolTaskExecutor")
    @EventListener(SmsEvent.class)
    public void sendSms(SmsEvent smsEvent){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", key);
        params.add("user_id",id);
        params.add("sender", sender);
        params.add("receiver", smsEvent.getPhoneNumber());
        params.add("msg", smsEvent.getMessage());

        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity formEntity = new HttpEntity<>(params, headers);

        String result = "";
        System.out.println("HELLO, WORLD!!!!");
        try {
            System.out.println("주문 완료 sms를 발송합니다.");
            System.out.println("message :" + smsEvent.getMessage());
            System.out.println("receiver : " + smsEvent.getPhoneNumber());
            result = restTemplate.postForObject("https://apis.aligo.in/send/", formEntity, String.class);
            System.out.println(result);
        }catch (Exception e){

        }
    }

}
