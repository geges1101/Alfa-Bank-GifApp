package com.geges.gifService.App.Action;

import com.geges.gifService.App.Interface.GifInterface;
import com.geges.gifService.client.GifClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

//Приложение для работы с giphy.com

@Service
public class GiphyApp implements GifInterface {

    //Клиент giphy.com

    private GifClient gifClient;
    @Value("${giphy.api.key}")
    private String apiKey;

    @Autowired
    public GiphyApp(GifClient gifClient) {
        this.gifClient = gifClient;
    }

    //Отправляем ответ/гифку ResponseEntity

    @Override
    public ResponseEntity<Map> getGif(String tag) {
        ResponseEntity<Map> result = gifClient.getNextGif(this.apiKey, tag);
        result.getBody().put("compareResult", tag);
        return result;
    }
}
