package com.geges.gifService.App.Interface;

import java.util.List;
import java.util.Set;

public interface RatesInterface {

    int getKey(String code);

    void updateRates();

    List<String> getRates();
}
