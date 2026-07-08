package com.pm.authservice.helper;

import com.pm.authservice.dto.ResponseDTO;

public class ResponseMapper {

    public static <T> ResponseDTO<T> success(String message, T data) {
        ResponseDTO<T> response = new ResponseDTO<>();

        response.setData(data);
        response.setMessage(message);
        response.setSuccess(true);

        return response;
    }

    public static <T> ResponseDTO<T> failed(T data) {
        ResponseDTO<T> response = new ResponseDTO<>();

        response.setData(data);
        response.setMessage("Something wrong");
        response.setSuccess(false);

        return response;
    }

    public static <T> ResponseDTO<Void> businesError(String message) {
        ResponseDTO<Void> response = new ResponseDTO<>();

        response.setData(null);
        response.setMessage(message);
        response.setSuccess(false);

        return response;
    }
}
