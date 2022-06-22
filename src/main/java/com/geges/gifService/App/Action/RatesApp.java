package com.geges.gifService.App.Action;

// Приложение для работы с openexchangerates.org

import com.geges.gifService.App.Interface.RatesInterface;
import com.geges.gifService.RatesModel.Rates;
import com.geges.gifService.client.OERClient;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Set;

@Service
public class RatesApp implements RatesInterface {
    private Rates prevRates;
    private Rates currRates;

    private OERClient oerClient;
    private SimpleDateFormat dateFormat;



    @Override
    public int getKey(String code) {
        return 0;
    }

    @Override
    public void updateRates() {

    }

    @Override
    public Set<String> getRates() {
        return null;
    }
}
