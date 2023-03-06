package dev.tpcoder.svcdownstream.external;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange(accept = MediaType.APPLICATION_JSON_VALUE)
public interface VirtualClient {
    @GetExchange("/case1")
    Mono<Message> testingRequest(@RequestParam int slowtime);
}
