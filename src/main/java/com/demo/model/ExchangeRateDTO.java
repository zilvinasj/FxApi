package com.demo.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.demo.configuration.LocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ExchangeRate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("FxRate")
public class ExchangeRateDTO
{

    @JsonProperty("Dt")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "date", updatable = false, nullable = false)
    private LocalDate effectiveDate;
    @Column(name = "name", updatable = false, nullable = false)
    private String currency;
    @JsonProperty("Tp")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonProperty("CcyAmt")
    private List<Currency> exchangeRates;
    @Id
    @GeneratedValue
    private Long identifier;

}
