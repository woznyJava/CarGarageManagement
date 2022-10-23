package com.example.cargaragemanagement.exception;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExceptionDto {

    private String message;
    private LocalDateTime time;

    public static ExceptionDto fromException(Exception e) {
        return ExceptionDto.builder()
                .message(e.getMessage())
                .time(LocalDateTime.now())
                .build();
    }
}