package com.study.junitproject.web;

import com.study.junitproject.web.dto.response.BookResponseDto;
import com.study.junitproject.web.dto.request.BookSaveRequestDto;
import com.study.junitproject.web.dto.response.CMResponseDto;
import com.study.junitproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    @PostMapping("/api/v1/book")
    public ResponseEntity<?> create(@RequestBody @Valid BookSaveRequestDto bookSaveRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }

        BookResponseDto bookResponseDto = bookService.create(bookSaveRequestDto);

        return new ResponseEntity<>(CMResponseDto.builder().code(1).msg("책 등록 성공").body(bookResponseDto).build(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getList() {
        return null;
    }

    public ResponseEntity<?> get() {
        return null;
    }

    public ResponseEntity<?> delete() {
        return null;
    }

    public ResponseEntity<?> update() {
        return null;
    }

}
