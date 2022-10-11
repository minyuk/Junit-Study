package com.study.junitproject.service;

import com.study.junitproject.domain.Book;
import com.study.junitproject.domain.BookRepository;
import com.study.junitproject.dto.BookResponseDto;
import com.study.junitproject.dto.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto create(BookSaveRequestDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());

        return new BookResponseDto().toDto(bookPS);
    }

    public List<BookResponseDto> getList() {
        return bookRepository.findAll().stream()
                .map(new BookResponseDto()::toDto)
                .collect(Collectors.toList());
    }

    public BookResponseDto get(Long id) {
        Book bookPS = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 도서를 찾을 수 없습니다."));

        return new BookResponseDto().toDto(bookPS);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}
