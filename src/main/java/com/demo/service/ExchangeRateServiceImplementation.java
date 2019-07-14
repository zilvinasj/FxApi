/**
 * 
 */
package com.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.exception.CurrencyExchangeException;
import com.demo.model.ExchangeRateDTO;
import com.demo.model.ExchangeResponse;
import com.demo.repo.ExchangeRateDTORepository;

/**
 * @author ZJankunas
 *
 */
@Service
public class ExchangeRateServiceImplementation implements ExchangeRateService
{

    /**
     * 
     */
    private static final String COULD_NOT_FIND_EXCHANGE_RATE_FOR = "Could not find Exchange rate for ";

    @Autowired
    private ExchangeRateDTORepository repository;

    /*
     * 
     * 
     * /* (non-Javadoc)
     * 
     * @see com.demo.service.ExchangeRateService#addCurrency(com.demo.model.
     * ExchangeRateDTO)
     */
    @Override
    public void addCurrencies(List<ExchangeRateDTO> exchangeRates)
    {
        repository.saveAll(exchangeRates);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.demo.service.ExchangeRateService#exchangeCurrency(java.lang.String,
     * java.lang.String)
     */
    @Override
    public ExchangeResponse exchangeCurrencyFromEur(String currency, BigDecimal amount)
    {
        ExchangeRateDTO response = repository.findByCurrencyAndEffectiveDate(currency, LocalDate.parse("2019-07-12"))
                .orElseThrow(() -> new CurrencyExchangeException(COULD_NOT_FIND_EXCHANGE_RATE_FOR, currency));
        BigDecimal exchangeRate = response.getExchangeRates().stream().filter(e -> e.getCurrencyCode().equals(currency))
                .findFirst().get().getAmount();
        return ExchangeResponse.builder().currency("EUR").targetCurrency(currency).rate(exchangeRate)
                .intakeAmount(amount).resultAmount(amount.multiply(exchangeRate)).build();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.demo.service.ExchangeRateService#exchangeCurrency(java.lang.String,
     * java.lang.String)
     */
    @Override
    public ExchangeResponse exchangeCurrencyToEur(String currency, BigDecimal amount)
    {
        // should be localdate.now but could not get the scheduler to work due
        // to too many requests to LB
        ExchangeRateDTO response = repository.findByCurrencyAndEffectiveDate(currency, LocalDate.parse("2019-07-12"))
                .orElseThrow(() -> new CurrencyExchangeException(COULD_NOT_FIND_EXCHANGE_RATE_FOR, currency));
        BigDecimal exchangeRate = BigDecimal.ONE.divide(response.getExchangeRates().stream()
                .filter(e -> e.getCurrencyCode().equals(currency)).findFirst().get().getAmount(), 2,
                RoundingMode.HALF_UP);
        return ExchangeResponse.builder().currency(currency).targetCurrency("EUR").rate(exchangeRate)
                .intakeAmount(amount).resultAmount(amount.multiply(exchangeRate)).build();
    }

    /*
     * 
     * (non-Javadoc)
     * 
     * 
     * @see com.demo.service.ExchangeRateService#getAll(java.lang.String,
     * java.time.LocalDate)
     */
    @Override
    public ExchangeRateDTO getForDate(String currencyName, LocalDate date)
    {
        return repository.findByCurrencyAndEffectiveDate(currencyName, date)
                .orElseThrow(() -> new CurrencyExchangeException(COULD_NOT_FIND_EXCHANGE_RATE_FOR, currencyName));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.demo.service.ExchangeRateService#getHistory(java.lang.String,
     * java.time.LocalDate)
     */
    @Override
    public List<ExchangeRateDTO> getHistory(String currencyName)
    {
        return repository.findAllByCurrency(currencyName);
    }

}
