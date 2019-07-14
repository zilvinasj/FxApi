/**
 * 
 */
package com.demo.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Collections;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.model.ErrorInfo;
import com.demo.model.ResponseWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZJankunas
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler
{

    /**
     * This method handles MethodArgumentTypeMismatchException exceptions.
     *
     *
     * @param e
     *            The exception
     * @return ServiceResponse ResponseWrapper
     */
    @ResponseStatus(BAD_REQUEST)

    @ResponseBody
    public ResponseWrapper handle(MissingServletRequestParameterException e)
    {
        log.error(e.toString());
        return buildResponse(e.getMessage());

    }

    /**
     * This method handles HttpRequestMethodNotSupportedException exceptions.
     *
     *
     * @param e
     *            The exception
     * @return ServiceResponse ResponseWrapper
     */
    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponseWrapper handle(HttpRequestMethodNotSupportedException e)
    {
        log.error(e.toString());
        return buildResponse(e.getMessage());

    }

    /**
     * Catch all for exceptions not handled explicitly in other methods in this
     * class.
     * 
     * @param e
     *            Exception
     * @return response
     */
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseWrapper handle(Exception e)
    {
        log.error(e.toString());
        return buildResponse(e.getMessage());

    }

    /**
     * This method handles MethodArgumentTypeMismatchException exceptions.
     *
     *
     * @param e
     *            The exception
     * @return ServiceResponse ResponseWrapper
     */
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ResponseWrapper handle(MethodArgumentTypeMismatchException e)
    {
        log.error(e.toString());

        return buildResponse(e.getMessage());

    }

    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public ResponseWrapper handle(CurrencyExchangeException e)
    {
        log.error(e.toString());
        return buildResponse(e.getMessage());
    }

    private ResponseWrapper buildResponse(String message)
    {
        ResponseWrapper response = new ResponseWrapper();
        ErrorInfo error = new ErrorInfo();
        error.setDescription(message);
        response.setErrors(Collections.singletonList(error));
        return response;
    }

}
