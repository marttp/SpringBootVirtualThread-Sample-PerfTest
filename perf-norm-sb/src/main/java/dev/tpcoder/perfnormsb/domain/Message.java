package dev.tpcoder.perfnormsb.domain;

import org.springframework.data.annotation.Id;

public class Message {

    @Id
    private Long id;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Message generateMockMessage(Long id, String txt) {
        var message = new Message();
        message.setId(id);
        message.setMessage(txt);
        return message;
    }
}
