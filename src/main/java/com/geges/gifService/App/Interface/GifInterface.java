package com.geges.gifService.App.Interface;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GifInterface {
    ResponseEntity<Map> getGif(String tag);
}
