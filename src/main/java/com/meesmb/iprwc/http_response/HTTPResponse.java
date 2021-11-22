package com.meesmb.iprwc.http_response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class HTTPResponse<T> {
    /**
     * the response code, mostly "FAILURE" or "SUCCESS"
     */
    private HTTPResponseCode response;

    /**
     * the error message
     */
    private String errorMessage;
    private T data;

    private HTTPResponse(HTTPResponseCode response, String error, T data) {
        this.response = response;
        this.errorMessage = error;
        this.data = data;
    }

    public HTTPResponseCode getResponse() {
        return response;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getData() {
        return data;
    }

    /** checks if this HTTPResponse is a success, is ignored by json
     * @return whether this HTTPResponse contains a success
     */
    @JsonIgnore
    public boolean isSuccess() {
        return response.equals(HTTPResponseCode.SUCCESS);
    }

    public static <T> HTTPResponse<T> returnSuccess(T data) {
        return new HTTPResponse<T>(HTTPResponseCode.SUCCESS, "", data);
    }

    public static <T> HTTPResponse<T> returnFailure(String message) {
        return new HTTPResponse<T>(HTTPResponseCode.FAILURE, message, null);
    }

}