package com.socialnetwork.deploymentapp;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")  // Allow Angular to call this
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello from Spring Boot! 👋";
    }

    @GetMapping("/api/message")
    public Message getMessage() {
        return new Message("Welcome to our test project!", "This is working perfectly.");
    }
}

class Message {
    private String title;
    private String content;

    public Message(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getters
    public String getTitle() { return title; }
    public String getContent() { return content; }
}