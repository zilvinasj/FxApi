package com.demo.service;

import java.io.IOException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.demo.model.Wrapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SampleJob implements Job
{

    private ExchangeRateServiceImplementation service;

    private RestTemplate caller;

    XmlMapper xmlMapper;

    private String lbUrl;

    public SampleJob(ExchangeRateServiceImplementation service, RestTemplate caller, XmlMapper xmlMapper,
            @Value("${exchange-config.url}") String lbUrl)
    {
        this.service = service;
        this.caller = caller;
        this.xmlMapper = xmlMapper;
        this.lbUrl = lbUrl;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException
    {

        log.info("Executing job");

        Wrapper list;
        try
        {
            ResponseEntity<String> response = caller.getForEntity(lbUrl, String.class);
            list = xmlMapper.readValue(response.getBody(), Wrapper.class);
            list.getCard().stream().forEach(e -> e.setCurrency(e.getExchangeRates().get(1).getCurrencyCode()));
            service.addCurrencies(list.getCard());

        }
        catch (RestClientException | IOException e)
        {
            try
            {
                Thread.sleep(15000);
                this.execute(context);
            }
            catch (InterruptedException e1)
            {
                log.error("failed : {}", e1);
                this.execute(context);
            }

        }

    }
}
