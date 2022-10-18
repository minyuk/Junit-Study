package com.study.junitproject.web;

import com.study.junitproject.web.dto.response.BookListResponseDto;
import com.study.junitproject.web.dto.response.BookResponseDto;
import com.study.junitproject.web.dto.request.BookSaveRequestDto;
import com.study.junitproject.web.dto.response.CMResponseDto;
import com.study.junitproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }

        BookResponseDto bookResponseDto = bookService.create(bookSaveRequestDto);

        return new ResponseEntity<>(CMResponseDto.builder().code(1).msg("책 등록 성공").body(bookResponseDto).build(), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getList() {
        BookListResponseDto bookListResponseDto = bookService.getList();

        return new ResponseEntity<>(CMResponseDto.builder().code(1).msg("책 목록 조회 성공").body(bookListResponseDto).build(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        BookResponseDto bookResponseDto = bookService.get(id);

        return new ResponseEntity<>(CMResponseDto.builder().code(1).msg("책 단건 조회 성공").body(bookResponseDto).build(), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);

        return new ResponseEntity<>(CMResponseDto.builder().code(1).msg("책 삭제 성공").body(null).build(), HttpStatus.OK);
    }

    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid BookSaveRequestDto bookSaveRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }

        BookResponseDto bookResponseDto = bookService.update(id, bookSaveRequestDto);

        return new ResponseEntity<>(CMResponseDto.builder().code(1).msg("책 수정 성공").body(bookResponseDto).build(), HttpStatus.OK);
    }

}
