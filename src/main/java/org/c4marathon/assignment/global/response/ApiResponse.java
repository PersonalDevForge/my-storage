package org.c4marathon.assignment.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import org.c4marathon.assignment.global.response.enums.ResultCode;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ApiResponse<T> {
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PageResponse page;

    private ApiResponse(String code, String message, T data, PageResponse page) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.page = page;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(),
                null,
                null
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(),
                data,
                null
        );
    }

    public static <T> ApiResponse<List<T>> success(List<T> data) {
        return new ApiResponse<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(),
                data,
                null
        );
    }

    public static <T> ApiResponse<List<T>> success(Page<T> data) {
        return new ApiResponse<>(
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(),
                data.getContent(),
                PageResponse.from(data)
        );
    }

    public static <T> ApiResponse<T> failure(ResultCode resultCode) {
        return new ApiResponse<>(
                resultCode.getCode(),
                resultCode.getMessage(),
                null,
                null
        );
    }

    public static <T> ApiResponse<T> failure(ResultCode resultCode, String message) {
        return new ApiResponse<>(
                resultCode.getCode(),
                message,
                null,
                null
        );
    }

    @Getter
    @ToString
    public static class PageResponse {
        private final int number;
        private final int size;
        private final int totalCount;

        private PageResponse(int number, int size, int totalCount) {
            this.number = number;
            this.size = size;
            this.totalCount = totalCount;
        }

        public static PageResponse from(Page<?> page) {
            return new PageResponse(
                    page.getNumber(),
                    page.getSize(),
                    (int)page.getTotalElements()
            );
        }
    }
}
