package com.study.junitproject.service;

import com.study.junitproject.domain.Book;
import com.study.junitproject.domain.BookRepository;
import com.study.junitproject.web.dto.response.BookListResponseDto;
import com.study.junitproject.web.dto.response.BookResponseDto;
import com.study.junitproject.web.dto.request.BookSaveRequestDto;
import com.study.junitproject.util.MailSender;
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
    private final MailSender mailSender;

    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto create(BookSaveRequestDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());

        if (!mailSender.send()) {
            throw new RuntimeException("메일지 전송되지 않았습니다.");
        }

        return bookPS.toDto();
    }

    public BookListResponseDto getList() {
        List<BookResponseDto> bookResponseDtos = bookRepository.findAll().stream()
//                .map((bookPS) -> new BookResponseDto().toDto(bookPS))
                .map(Book::toDto)
                .collect(Collectors.toList());

        return BookListResponseDto.builder().bookList(bookResponseDtos).build();
    }

    public BookResponseDto get(Long id) {
        Book bookPS = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 도서를 찾을 수 없습니다."));

        return bookPS.toDto();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto update(Long id, BookSaveRequestDto dto) {
        Book bookPS = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 도서를 찾을 수 없습니다."));

        bookPS.update(dto.getTitle(), dto.getAuthor());

        return bookPS.toDto();
    }
}
