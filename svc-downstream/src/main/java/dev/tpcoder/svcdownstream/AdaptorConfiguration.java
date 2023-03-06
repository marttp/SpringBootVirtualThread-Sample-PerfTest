package dev.tpcoder.svcdownstream;

import dev.tpcoder.svcdownstream.external.NormalClient;
import dev.tpcoder.svcdownstream.external.VirtualClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Configuration
public class AdaptorConfiguration {
    private final Logger logger = LoggerFactory.getLogger(AdaptorConfiguration.class);

    @Value("${service.normal}")
    private String normalSvcUrl;
    @Value("${service.virtual}")
    private String virtualThreadSvcUrl;

    @Bean
    public HttpClientAdapter normSbClientAdaptor(WebClient.Builder builder) {
        Function<ClientResponse, Mono<? extends Throwable>> customErrorHandler =
                resp -> resp.bodyToMono(String.class)
                        .flatMap(body -> {
                            logger.error(body);
                            return Mono.error(new RuntimeException());
                        });
        var client = builder.baseUrl(normalSvcUrl)
                .defaultHeader("requestUid", UUID.randomUUID().toString())
                .defaultStatusHandler(HttpStatusCode::isError, customErrorHandler);
        return WebClientAdapter.forClient(client.build());
    }

    @Bean
    public NormalClient normalClient(@Qualifier("normSbClientAdaptor") HttpClientAdapter webClientAdapter) {
        return HttpServiceProxyFactory
                .builder(webClientAdapter)
                .build()
                .createClient(NormalClient.class);
    }

    @Bean
    public HttpClientAdapter vtSbClientAdaptor(WebClient.Builder builder) {
        Function<ClientResponse, Mono<? extends Throwable>> customErrorHandler =
                resp -> resp.bodyToMono(String.class)
                        .flatMap(body -> {
                            logger.error(body);
                            return Mono.error(new RuntimeException());
                        });
        var client = builder.baseUrl(virtualThreadSvcUrl)
                .defaultHeader("requestUid", UUID.randomUUID().toString())
                .defaultStatusHandler(HttpStatusCode::isError, customErrorHandler);
        return WebClientAdapter.forClient(client.build());
    }

    @Bean
    public VirtualClient virtualClient(@Qualifier("vtSbClientAdaptor") HttpClientAdapter webClientAdapter) {
        return HttpServiceProxyFactory
                .builder(webClientAdapter)
                .build()
                .createClient(VirtualClient.class);
    }
}
