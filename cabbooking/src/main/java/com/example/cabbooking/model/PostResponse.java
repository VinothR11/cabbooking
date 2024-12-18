package com.example.cabbooking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private String status;
    private String message;

    // No-argument constructor
    public PostResponse() {
    }

    // Parameterized constructor
    public PostResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
