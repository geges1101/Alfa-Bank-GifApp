package com.geges.gifService.App.Action;

// Приложение для работы с openexchangerates.org

import com.geges.gifService.App.Interface.RatesInterface;
import com.geges.gifService.RatesModel.Rates;
import com.geges.gifService.client.OERClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RatesApp implements RatesInterface {
    @Value("${openexchangerates.app.id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;

    private Rates prevRates;
    private Rates currRates;

    private OERClient oerClient;
    private SimpleDateFormat date;
    private SimpleDateFormat time;

    //Инициализация приложения
    @Autowired
    public RatesApp(OERClient oerClient,
                    @Qualifier("date_bean") SimpleDateFormat date,
                    @Qualifier("time_bean") SimpleDateFormat time){
        this.oerClient = oerClient;
        this.date = date;
        this.time = time;
    }

    @Override
    public int getKey(String code) {
        this.updateRates();
        Double prevCoefficient = this.calculateRate(this.prevRates, code);
        Double currentCoefficient = this.calculateRate(this.currRates, code);
        return prevCoefficient != null && currentCoefficient != null
                ? Double.compare(currentCoefficient, prevCoefficient)
                : -101;
    }

    //Обновление котировок
    @Override
    public void updateRates() {
        long currTime = System.currentTimeMillis();
        this.(currentTime);
        this.refreshPrevRates(currentTime);
    }

    private void updateCurrentRates(long time) {
        if (
                this.currRates == null ||
                        !timeFormat.format(Long.valueOf(this.currentRates.getTimestamp()) * 1000)
                                .equals(timeFormat.format(time))
        ) {
            this.currentRates = openExchangeRatesClient.getLatestRates(this.appId);
        }
    }

    //Получкеие доступных котировок валют
    @Override
    public List<String> getRates() {
        if(this.currRates.getRates() != null)
            return new ArrayList<>(this.currRates.getRates().keySet());
        return null;
    }

    //Обновляет курсы со сменой дня, сравнивая с предыдущими
    private void updatePrevRates(long time){
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTimeInMillis(time);
        prevCalendar.add(Calendar.DAY_OF_YEAR, - 1);
        String currDate = date.format(prevCalendar.getTime());
        String prevDate = date.format(prevCalendar.getTime());

        if ((!date.format(Long.valueOf(this.prevRates.getTimestamp()) * 1000)
                .equals(prevDate) && !date.format(Long.valueOf(this.prevRates.getTimestamp()) * 1000)
                .equals(currDate) ) || this.prevRates == null){
            this.prevRates = oerClient.getHistoricalRates(prevDate, appId);
        }
    }

    //Определение курса в отношении к валютным базам, округление значений

    private Double calculateRate(Rates rates, String code){
        Double result = null;
        Double targetRate = null;
        Double appRate = null;
        Double defaultRate = null;
        Map<String, Double> map = null;

        if(rates != null && rates.getRates() != null){
            map = rates.getRates();
            targetRate = map.get(code);
            appRate = map.get(this.base);
            defaultRate = map.get(rates.getBase());
        }

        if(targetRate != null &&
                appRate != null &&
                defaultRate != null
        ) {
            result = new BigDecimal(
                    (defaultRate / appRate) * targetRate
            )
                    .setScale(2, RoundingMode.UP)
                    .doubleValue();
        }
        return result;
    }
}

