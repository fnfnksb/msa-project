package com.msa.exception;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.msa.model.Result;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * custom exception handler 
 * https://riverblue.tistory.com/69
 * @author fnfnksb@gmail.com
 */
@Slf4j
@Order(-2)
@Component
public class GatewayExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GatewayExceptionHandler(ErrorAttributes errorAttributes, Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageReaders(configurer.getReaders());
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
       Map<String, Object> map = getErrorAttributes(request, ErrorAttributeOptions.defaults());
       Throwable throwable = super.getError(request);
       Result result = null;

       if (throwable instanceof AdminAuthException) {
           AdminAuthException ex = (AdminAuthException) getError(request);

           result = new Result(ex.getResultCode(), ex.getResultMsg());
       } else {
           log.info("사용자 정의 exception 아님");
           result = new Result();
       }

       log.error("GatewayExceptionHandler", throwable);
//       return ServerResponse.temporaryRedirect(URI.create("/")).build(); // 로그인페이지로 이동됨. 근데 데이터 세팅 어떡함?.
       return ServerResponse.status(HttpStatus.BAD_REQUEST)
              .contentType(MediaType.APPLICATION_JSON)
              .body(BodyInserters.fromValue(result));
    }

}