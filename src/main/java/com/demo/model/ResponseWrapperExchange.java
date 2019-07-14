/**
 * 
 */
package com.demo.model;

import java.util.List;

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
public class ResponseWrapperExchange
{

    private List<ExchangeResponse> response;
    private List<ErrorInfo> errors;

}
