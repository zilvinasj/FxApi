/**
 * 
 */
package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Getter;
import lombok.ToString;

/**
 * @author ZJankunas
 *
 */
@ToString
@Getter
public class Wrapper
{
    @JacksonXmlElementWrapper(localName = "FxRate")
    @JsonProperty("FxRate")
    private List<ExchangeRateDTO> card = new ArrayList<>();

    @JsonSetter
    public void setCard(ExchangeRateDTO card)
    {
        this.card.add(card);
    }
}
