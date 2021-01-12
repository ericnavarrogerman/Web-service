package com.pqr.demopqr.util;

import com.pqr.demopqr.util.enums.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class BackendResponse implements Serializable {
    private ResponseStatus status;
    private HttpStatus httpStatus;
    private Object content;

    public BackendResponse(Object content) {
        this(ResponseStatus.OK, content);
    }

    public BackendResponse(ResponseStatus status, Object content) {
        this.status = status;
        this.content = content;
    }

    public BackendResponse(HttpStatus status, Object content) {
        this.httpStatus = status;
        this.content = content;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}