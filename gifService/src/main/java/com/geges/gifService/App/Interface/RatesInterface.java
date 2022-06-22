package com.geges.gifService.App.Interface;

import java.util.Set;

public interface RatesInterface {

    int getKey(String code);

    void updateRates();

    Set<String> getRates();
}
