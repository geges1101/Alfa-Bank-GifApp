package com.geges.gifService.controller;

import com.geges.gifService.App.Interface.GifInterface;
import com.geges.gifService.App.Interface.RatesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/gg")
public class AppControler {
    private RatesInterface ratesInterface;
    private GifInterface gifService;
    @Value("${giphy.rich}")
    private String richTag;
    @Value("${giphy.poor}")
    private String poorTag;
    @Value("${giphy.zero}")
    private String whatTag;
    @Value("${giphy.error}")
    private String errorTag;

    @Autowired
    public AppControler(
            RatesInterface ratesInterface,
            GifInterface gifInterface
    ) {
        this.ratesInterface = ratesInterface;
        this.gifService = gifService;
    }

     //Возвращает доступные валюты
    @GetMapping("/getcodes")
    public Set<String> getValues() {
        return ratesInterface.getRates();
    }

    /**
     * Получает гифку для отправки клиенту
     * исходя из резултата сравнения курса в openExchangeRatesService
     *
     * @param code
     * @return
     */
    @GetMapping("/getgif/{code}")
    public ResponseEntity<Map> getGif(@PathVariable String code) {
        ResponseEntity<Map> result = null;
        int gifKey = -101;
        String gifTag = this.errorTag;
        if (code != null) {
            gifKey = ratesInterface.getKey(code);
        }
        switch (gifKey) {
            case 1:
                gifTag = this.richTag;
                break;
            case -1:
                gifTag = this.poorTag;
                break;
            case 0:
                gifTag = this.whatTag;
                break;
        }
        result = gifService.getGif(gifTag);
        return result;
    }
}
