package org.example.savemysoulrestapi.controller;

import org.example.savemysoulrestapi.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("savemysoul_api")
public class MessageController {
    @Value("${telegram.bot.api.url}")
    private String bot_api_url;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("sendSOS")
    public String sendSOS(@RequestBody User user) {
        Long chatId = Long.valueOf(user.getId());
        String message = user.getMessage();
        String url =  String.format("%s/sendMessage?chat_id=%d&text=%s", bot_api_url, chatId, message);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
            System.out.println(response);
            return "SOS надіслано!";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Помилка в надсилані SOS: " + e.getMessage();
        }
    }
}