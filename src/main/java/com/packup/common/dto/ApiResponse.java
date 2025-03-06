package com.packup.common.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    /**
     * HTTP 상태 코드
     */
    private int status;
    /**
     * 응답 메세지
     * 200 인 경우 success, 실패한 경우 실패한 이유 ex) 사용자의 아이디가 중복되었습니다.
     * 프론트에서는 해당 메세지를 바로 활용
     */
    private String message;
    /**
     * 반환하는 데이터
     * 어차피 Jackson 라이브러리가 JSON 형식으로 변환
     */
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }


}
