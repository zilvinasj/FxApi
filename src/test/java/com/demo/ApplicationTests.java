package com.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.controller.CurrencyController;
import com.demo.exception.CurrencyExchangeException;
import com.demo.model.ResponseWrapperExchange;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests
{

    @Autowired
    CurrencyController controller;

    private static boolean setUpIsDone = false;

    @Before
    public void setUp() throws IOException
    {
        if (setUpIsDone)
        {
            return;
        }
        controller.populate();
        setUpIsDone = true;
    }

    @Test
    public void testSuccessExchangeToEur() throws IOException
    {
        ResponseEntity<ResponseWrapperExchange> response = controller.exchangeToEur("GBP", BigDecimal.valueOf(500));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNull(response.getBody().getErrors());
        assertEquals("GBP", response.getBody().getResponse().get(0).getCurrency());
        assertEquals("EUR", response.getBody().getResponse().get(0).getTargetCurrency());
    }

    @Test
    public void testSuccessfulExchangeFromEUR() throws IOException
    {
        ResponseEntity<ResponseWrapperExchange> response = controller.exchangeEur("GBP", BigDecimal.valueOf(500));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertNull(response.getBody().getErrors());
        assertEquals("EUR", response.getBody().getResponse().get(0).getCurrency());
        assertEquals("GBP", response.getBody().getResponse().get(0).getTargetCurrency());
    }

    @Test
    public void test() throws IOException
    {
        try
        {
            controller.exchangeEur("ZZZ", BigDecimal.valueOf(500));
            fail();
        }
        catch (CurrencyExchangeException e)
        {
            assertTrue(e.getMessage().contains("Could not find Exchange rate for "));
        }

    }

}
