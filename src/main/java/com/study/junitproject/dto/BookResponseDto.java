package com.study.junitproject.dto;

import com.study.junitproject.domain.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;

    public BookResponseDto toDto(Book bookPS) {
        this.id = bookPS.getId();
        this.title = bookPS.getTitle();
        this.author = bookPS.getAuthor();
        return this;
    }
}
