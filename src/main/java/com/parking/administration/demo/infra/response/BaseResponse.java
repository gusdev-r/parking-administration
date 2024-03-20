package com.parking.administration.demo.infra.response;


import lombok.Data;
import org.springframework.web.ErrorResponse;
@Data
public class BaseResponse<T> {
    private Boolean success;
    private String message;
    private T result;
    private ErrorResponse error;

    public BaseResponse(Boolean success, String message, T result, ErrorResponse error) {
        this.success = success;
        this.message = message;
        this.result = result;
        this.error = error;
    }
    public static <T> BaseResponseBuilder<T> builder() {
        return new BaseResponseBuilder<T>()
                .success(true)
                .message("Default message")
                .result(null)
                .error(null);
    }

    public static class BaseResponseBuilder<T> {
        private Boolean success;
        private String message;
        private T result;
        private ErrorResponse error;

        private BaseResponseBuilder() {

        }

        public BaseResponseBuilder<T> success(Boolean success) {
            this.success = success;
            return this;
        }

        public BaseResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public BaseResponseBuilder<T> result(T result) {
            this.result = result;
            return this;
        }

        public BaseResponseBuilder<T> error(ErrorResponse error) {
            this.error = error;
            return this;
        }

        public BaseResponse<T> build() {
            return new BaseResponse<>(success, message, result, error);
        }
    }
}
