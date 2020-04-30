package com.curevent.services.rest;

import com.curevent.exceptions.InvalidArgumentException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Getter
@Setter
@Slf4j
public abstract class RESTClient <T> {

    @Value("${webClient.timeout:5000}")
    private int TIMEOUT_IN_MILLIS;

    private Class<T> tClass;
    private String URI;
    private WebClient restClient;
    private String host;
    private String path;
    private String scheme;

    public String getScheme(){return "https";}

    public T get(MultiValueMap<String, String> params) {
        log.info("Starting request to " + getHost());
        try {
            return restClient
                    .method(HttpMethod.GET)
                    .uri(uriBuilder -> uriBuilder
                            .scheme(getScheme())
                            .queryParams(params)
                            .host(getHost())
                            .path(getPath())
                            .build()
                    )
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> {
                        log.error("Error in request to "+getHost(),getPath()+params);
                        throw new InvalidArgumentException(response.statusCode().getReasonPhrase());
                    })
                    .bodyToMono(getTClass())
                    .block();
//            Catching Exception class, because if it's not working - just omit
        } catch (Exception exception){
            return null;
        }
    }

    public RESTClient(){
        this.restClient = WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(TcpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT_IN_MILLIS)
                        .doOnConnected(connection -> {
                            connection.addHandlerLast(new ReadTimeoutHandler( TIMEOUT_IN_MILLIS));
                            connection.addHandlerLast(new WriteTimeoutHandler( TIMEOUT_IN_MILLIS));
                        }))))
                .build();
    }
}
