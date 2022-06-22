package com.geges.gifService.client;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GifClient {

    ResponseEntity<Map> getNextGif(String tag, String apiKey);
}
