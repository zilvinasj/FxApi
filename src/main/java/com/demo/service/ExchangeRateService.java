/**
 * 
 */
package com.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.demo.model.ExchangeRateDTO;
import com.demo.model.ExchangeResponse;

/**
 * @author ZJankunas
 *
 */
public interface ExchangeRateService
{

    public void addCurrencies(List<ExchangeRateDTO> exchangeRate);

    public ExchangeResponse exchangeCurrencyToEur(String currency, BigDecimal amount);

    public ExchangeResponse exchangeCurrencyFromEur(String currency, BigDecimal amount);

    public List<ExchangeRateDTO> getHistory(String currencyName);

    public ExchangeRateDTO getForDate(String currencyName, LocalDate date);

}
