package com.geges.gifService.App.Action;

import com.geges.gifService.RatesModel.Rates;
import com.geges.gifService.client.FeignOREClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("geges.com")
public class OERtest {
    @Value("${openexchangerates.base}")
    private String base;
    @Autowired
    private RatesApp exchangeRatesService;
    @MockBean
    private FeignOREClient openExchangeRatesClient;

    private Rates currentRates;
    private Rates prevRates;

    @Before
    public void init() {
        int time = 1609459199;
        this.currentRates = new Rates();
        this.currentRates.setTimestamp(time);
        this.currentRates.setBase("T_BASE");
        Map<String, Double> currentRatesMap = new HashMap<>();
        currentRatesMap.put("T1", 0.1);
        currentRatesMap.put("T2", 0.5);
        currentRatesMap.put("T3", 1.0);
        currentRatesMap.put(this.base, 73.108);
        currentRatesMap.put("T_BASE", 1.0);
        this.currentRates.setRates(currentRatesMap);

        time = 1609372799;
        this.prevRates = new Rates();
        this.prevRates.setTimestamp(time);
        this.prevRates.setBase("T_BASE");
        Map<String, Double> prevRatesMap = new HashMap<>();
        prevRatesMap.put("T1", 0.1);
        prevRatesMap.put("T2", 1.0);
        prevRatesMap.put("T3", 0.5);
        prevRatesMap.put(this.base, 73.108);
        prevRatesMap.put("T_BASE", 1.0);
        this.prevRates.setRates(prevRatesMap);

    }

    @Test
    public void whenPositiveChanges() {
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey("T3");
        assertEquals(1, result);
    }

    @Test
    public void whenNegativeChanges() {
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey("T2");
        assertEquals(-1, result);
    }

    @Test
    public void whenZeroChanges() {
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey("T1");
        assertEquals(0, result);
    }

    @Test
    public void whenGotNullOnInput() {
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey(null);
        assertEquals(-101, result);
    }

    @Test
    public void whenGotUnExistCharCode() {
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey("UNEXISTED");
        assertEquals(-101, result);
    }

    @Test
    public void whenGetList() {
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        List<String> result = exchangeRatesService.getRates();
        assertThat(result, containsInAnyOrder("T1", "T2", "T3", base, "T_BASE"));
    }

    @Test
    public void whenCurrentIsNull() {
        this.currentRates = null;
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey("T1");
        assertEquals(-101, result);
    }

    @Test
    public void whenPrevIsNull() {
        this.prevRates = null;
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey("T1");
        assertEquals(-101, result);
    }

    @Test
    public void whenCurrentAndPrevIsNull() {
        this.currentRates = null;
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey("T1");
        assertEquals(-101, result);
    }

    @Test
    public void whenAppBaseIsChanged() {
        this.currentRates.getRates().put(this.base, 73.945);
        Mockito.when(openExchangeRatesClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        int result = exchangeRatesService.getKey(this.base);
        assertEquals(0, result);
    }


}
