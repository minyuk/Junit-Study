package com.study.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;

    @Builder
    public BookResponseDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
