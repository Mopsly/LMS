package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsCounter {
    private static final Logger log = LoggerFactory.getLogger(StatisticsCounter.class);

    public void countHandlerCall(String request){
        log.info("Кто-то дернул нашу ручку в " + request);
    }

}
