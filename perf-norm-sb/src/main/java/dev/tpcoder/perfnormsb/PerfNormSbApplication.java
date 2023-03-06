package dev.tpcoder.perfnormsb;

import dev.tpcoder.perfnormsb.domain.Message;
import dev.tpcoder.perfnormsb.domain.TestRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PerfNormSbApplication {
    private final TestRepository testRepository;

    public PerfNormSbApplication(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PerfNormSbApplication.class, args);
    }

    @GetMapping("/case1")
    public ResponseEntity<Message> case1TestSlowResponse(@RequestParam int slowtime) throws InterruptedException {
        Thread.currentThread().sleep(Long.valueOf(slowtime));
        return ResponseEntity.ok(Message.generateMockMessage(1L, "Success"));
    }

    @GetMapping("/case3")
    public ResponseEntity<Message> case3TestSlowFromDataSource(@RequestParam int slowtime) {
        testRepository.executeSleep(slowtime);
        return ResponseEntity.ok(Message.generateMockMessage(1L, "Success"));
    }
}
