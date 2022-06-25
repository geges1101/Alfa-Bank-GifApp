package com.geges.gifService.App.Action;

import com.geges.gifService.client.FeignGiphyClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("geges.com")
class GifServiceApplicationTests {

	@Autowired
	private GiphyApp gifService;
	@MockBean
	private FeignGiphyClient gifClient;


	@Test
	void contextLoads() {
		ResponseEntity<Map> responseEntity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);
		Mockito.when(gifClient.getNextGif(anyString(), anyString()))
				.thenReturn(responseEntity);
		ResponseEntity<Map> result = gifService.getGif("control_test_word");
		Assertions.assertEquals("control_test_word", result.getBody().get("compareResult"));
	}

}
