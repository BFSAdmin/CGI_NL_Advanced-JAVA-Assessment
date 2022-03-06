package com.nl.cgi.bff.exception;

import com.nl.cgi.bff.model.response.CountryResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@UtilityClass
@Slf4j
public class ExceptionUtil {

    private static final String GENERIC_SERVICE_MESSAGE = "Internal server error please try later";

    public static Mono<Throwable> handleGenericWebClientException(final Throwable throwable) {
        if (throwable instanceof ServiceException) {
            return Mono.error(throwable);
        }
        //This is used to handle the generic exception thrown by down stream services ex: timeoutException
        return Mono.error(new ServiceException(ErrorDetail.INTERNAL_SERVER_ERROR, GENERIC_SERVICE_MESSAGE, throwable));
    }

    public static Mono<Throwable> handleErrorResponse(ClientResponse clientResponse, ServiceException exception) {

        if (clientResponse.statusCode().equals(HttpStatus.BAD_REQUEST)) {
            exception.setErrorDetail(ErrorDetail.CLIENT_BAD_REQUEST_EXCEPTION);
        } else if (clientResponse.statusCode().equals(HttpStatus.FORBIDDEN)) {
            exception.setErrorDetail(ErrorDetail.CLIENT_FORBIDDEN_EXCEPTION);
        } else if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
            exception.setErrorDetail(ErrorDetail.CLIENT_RESOURCE_NOT_FOUND_EXCEPTION);
        } else {
            exception.setErrorDetail(ErrorDetail.INTERNAL_SERVER_ERROR);
        }
        return Mono.error(exception);
    }


    public static Mono<Boolean> validateGetResponse(boolean response) {
       if (!response) {
           log.error("Unauthorized details return from PS");
            return Mono.error(new ServiceException(ErrorDetail.CLIENT_AUTHENTICATION_SERVICE_EXCEPTION, "No user returned from persistence service"));
        }
        log.info("authorized details return from PS {}",response);
        return Mono.just(response);
    }
    public static Mono<Boolean> validateSaveResponse(boolean response) {
        if (!response) {
            log.error("user is already exits");
            return Mono.error(new ServiceException(ErrorDetail.CLIENT_USER_EXITS_SERVICE_EXCEPTION, "user returned from persistence service"));
        }
        log.info("user details successfully saved {}",response);
        return Mono.just(true);
    }
    public static Mono<Boolean> validateResponse(boolean response) {
        if (!response) {
            log.info("Country details updated/Added Successfully");
            return Mono.error(new ServiceException(ErrorDetail.INTERNAL_SERVER_ERROR, "Country details is not saved"));
        }
        log.info("Country details updated/Added Successfully");
        return Mono.just(response);
    }
    public static Mono<CountryResponse> validateGetCountryResponse(CountryResponse countryResponse) {
       if (countryResponse==null || countryResponse.getCountryNames().isEmpty()) {
           log.error("Country details is empty");
           return Mono.error(new ServiceException(ErrorDetail.CLIENT_DETAILS_NOT_FOUND, "No country returned from persistence service"));
        }
        log.info("Country details return from PS");
        return Mono.just(countryResponse);
    }


}