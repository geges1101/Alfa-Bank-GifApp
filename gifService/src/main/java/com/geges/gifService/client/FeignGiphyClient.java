package com.geges.gifService.client;

//Feign клиент достаяющий случайнюу gif

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@org.springframework.cloud.openfeign.FeignClient(name = "Giphy", url = "${giphy.url.general}")
public interface FeignGiphyClient extends GifClient{

    @Override
    @GetMapping("/random")
    ResponseEntity<Map> getRandomGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );
}
