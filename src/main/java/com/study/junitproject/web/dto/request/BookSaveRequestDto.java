package com.study.junitproject.web.dto.request;

import com.study.junitproject.domain.Book;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data //Controller에서 Setter가 호출되면서 DTO에 값이 채워짐
public class BookSaveRequestDto {

    @Size(min = 1, max = 50)
    @NotBlank
    private String title;

    @Size(min = 2, max = 20)
    @NotBlank
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
