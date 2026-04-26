package com.renovar.resources.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StandardError {

    private Integer status;
    private String message;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Long timeStamp;

    public StandardError(Integer status, String message, Long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getTimeStamp() { return timeStamp; }
    public void setTimeStamp(Long timeStamp) { this.timeStamp = timeStamp; }

}