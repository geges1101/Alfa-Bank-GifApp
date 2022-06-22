package com.geges.gifService;

import com.geges.gifService.App.Interface.RatesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Launcher {
    private RatesInterface ratesPage;

    @Autowired
    public Launcher(RatesInterface ratesPage){
        this.ratesPage = ratesPage;
    }
    @PostConstruct
    public void startLaunch(){ratesPage.updateRates();}
}
