package com.demo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("CcyAmt")
public class Currency
{
    public Currency(String currencyCode, BigDecimal amount)
    {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    @JsonProperty("Ccy")
    @Column(name = "code", updatable = false)
    private String currencyCode;
    @JsonProperty("Amt")
    @Column(name = "amount", updatable = false)
    private BigDecimal amount;
    @Id
    @GeneratedValue
    private Long id;

    /**
     * @return the amount
     */
    public BigDecimal getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    /**
     * @return the currencyCode
     */
    public String getCurrencyCode()
    {
        return currencyCode;
    }

    /**
     * @param currencyCode
     *            the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

}
