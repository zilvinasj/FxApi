package com.demo.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.ResponseWrapper;
import com.demo.model.ResponseWrapperExchange;
import com.demo.model.Wrapper;
import com.demo.service.ExchangeRateServiceImplementation;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mchange.io.FileUtils;

@RestController
public class CurrencyController
{

    ExchangeRateServiceImplementation service;

    XmlMapper xmlMapper;

    @Autowired
    public CurrencyController(ExchangeRateServiceImplementation service, XmlMapper xmlMapper)
    {
        this.service = service;
        this.xmlMapper = xmlMapper;
    }

    @GetMapping(value = "/currencyForDate/{currencyName}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getCurrencyByNameAndDate(
            @PathVariable(name = "currencyName") String currencyName,
            @RequestParam(name = "date", required = false) String date)
    {

        if (date != null)
        {

            return new ResponseEntity<>(ResponseWrapper.builder()
                    .response(Collections.singletonList(service.getForDate(currencyName, LocalDate.parse(date))))
                    .errors(null).build(), HttpStatus.OK);
        }
        // if date not supplied return current date
        else
        {
            return new ResponseEntity<>(ResponseWrapper.builder()
                    .response(Collections.singletonList(service.getForDate(currencyName, LocalDate.now()))).errors(null)
                    .build(), HttpStatus.OK);

        }
    }

    @GetMapping(value = "/exchange/EUR/{currencyName}", produces = "application/json")
    public ResponseEntity<ResponseWrapperExchange> exchangeEur(@PathVariable(name = "currencyName") String currencyName,
            @RequestParam(name = "amount", required = false) BigDecimal amount)
    {

        return new ResponseEntity<>(ResponseWrapperExchange.builder()
                .response(Collections.singletonList(service.exchangeCurrencyFromEur(currencyName, amount))).build(),
                HttpStatus.OK);

    }

    @GetMapping(value = "/exchange/{currencyName}/EUR", produces = "application/json")
    public ResponseEntity<ResponseWrapperExchange> exchangeToEur(
            @PathVariable(name = "currencyName") String currencyName,
            @RequestParam(name = "amount", required = false) BigDecimal amount)
    {

        return new ResponseEntity<>(ResponseWrapperExchange.builder()
                .response(Collections.singletonList(service.exchangeCurrencyToEur(currencyName, amount))).build(),
                HttpStatus.OK);

    }

    @GetMapping(value = "/currencyHistory/{currencyName}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getCurrencyHistory(@PathVariable(name = "currencyName") String currencyName,
            @RequestParam(name = "date", required = false) String date)
    {
        return new ResponseEntity<>(ResponseWrapper.builder().response(service.getHistory(currencyName)).build(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/populate", produces = "application/json")
    public void populate() throws IOException
    {
        Wrapper list = xmlMapper.readValue(FileUtils.getContentsAsString(new File("src/main/resources/response2.xml")),
                Wrapper.class);
        list.getCard().stream().forEach(e -> e.setCurrency(e.getExchangeRates().get(1).getCurrencyCode()));
        service.addCurrencies(list.getCard());

    }
}
