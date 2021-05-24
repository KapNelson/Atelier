package com.web.atelier.security;

public class AuthenticationResponse {
    private final boolean success;
    private final String exception;
    private final String description;

    public AuthenticationResponse(boolean success, String exception, String description) {
        this.success = success;
        this.exception = exception;
        this.description = description;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getException() {
        return exception;
    }

    public String getDescription() {
        return description;
    }
}
