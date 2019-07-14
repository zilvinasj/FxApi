package com.demo.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.ExchangeRateDTO;

@Repository("repository")
public interface ExchangeRateDTORepository extends JpaRepository<ExchangeRateDTO, String>
{
    Optional<ExchangeRateDTO> findByCurrencyAndEffectiveDate(String name, LocalDate date);

    List<ExchangeRateDTO> findAllByCurrencyAndEffectiveDate(String name, LocalDate date);

    List<ExchangeRateDTO> findAllByCurrency(String name);

    List<ExchangeRateDTO> findAllByCurrencyAndExchangeRatesCurrencyCodeAndEffectiveDate(String name, String code,
            LocalDate date);

}
