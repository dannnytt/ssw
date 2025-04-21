package io.swagger.api;

import jakarta.annotation.Generated;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-25T14:57:37.893068418Z[GMT]")
public class ApiException extends Exception {
    private final int code;

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}