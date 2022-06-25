package com.geges.gifService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class AppConfig {

    //Преобразование даты с точностью до дня
    @Bean("date_bean")
    public SimpleDateFormat formatDate() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    //Преобразование даты с точностью до часа
    @Bean("time_bean")
    public SimpleDateFormat formatTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH");
    }

}
