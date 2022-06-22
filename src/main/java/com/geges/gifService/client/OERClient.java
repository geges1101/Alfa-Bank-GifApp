package com.geges.gifService.client;

import com.geges.gifService.RatesModel.Rates;

public interface OERClient {
    Rates getLatestRates(String appId);

    Rates getHistoricalRates(String appId, String date);
}
