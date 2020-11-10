package io.grupa.surveyapp.pojo;

import lombok.Data;

public @Data  class GenericDataResponse<T> implements Response {
    private T data;
    private String message;
    private String code;
    private boolean success;

    public GenericDataResponse(String message, boolean success, String error) {
        super();
        this.message = message;
        this.success = success;
        this.code = error;
    }

}
