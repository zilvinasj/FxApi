/**
 * 
 */
package com.demo.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ZJankunas
 *
 */
@Getter
@Setter
@Builder
public class ExchangeResponse
{

    private String currency;
    private String targetCurrency;
    private BigDecimal rate;
    private BigDecimal intakeAmount;
    private BigDecimal resultAmount;
}
