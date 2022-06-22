package com.geges.gifService.client;

import com.geges.gifService.RatesModel.Rates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.cloud.openfeign.FeignClient(name = "OERClient", url = "${openexchangerates.url.general}")
public interface FeignOREClient extends OERClient{

    @Override
    @GetMapping("/latest.json")
    Rates getLatestRates(
            @RequestParam("app_id") String appId
    );

    @Override
    @GetMapping("/historical/{date}.json")
    Rates getHistoricalRates(
            @PathVariable String date,
            @RequestParam("app_id") String appId
    );
}
