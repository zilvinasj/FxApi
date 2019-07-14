/**
 * 
 */
package com.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ZJankunas
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CurrencyExchangeException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String message;
    private String description;

}
