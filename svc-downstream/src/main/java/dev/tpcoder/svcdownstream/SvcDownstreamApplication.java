package dev.tpcoder.svcdownstream;

import dev.tpcoder.svcdownstream.external.Message;
import dev.tpcoder.svcdownstream.external.NormalClient;
import dev.tpcoder.svcdownstream.external.VirtualClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Set;

@SpringBootApplication
@RestController
public class SvcDownstreamApplication {

    private final NormalClient normalClient;
    private final VirtualClient virtualClient;

    public SvcDownstreamApplication(NormalClient normalClient, VirtualClient virtualClient) {
        this.normalClient = normalClient;
        this.virtualClient = virtualClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(SvcDownstreamApplication.class, args);
    }


    @GetMapping("/{id}")
    public Mono<Message> requestToExternal(@PathVariable int id) {
        var slowTime = 1000;
        if (!Set.of(0, 1).contains(id)) {
            throw new IllegalArgumentException("Unhandled request id");
        }
        return id == 0 ? normalClient.testingRequest(slowTime) : virtualClient.testingRequest(slowTime);
    }
}
