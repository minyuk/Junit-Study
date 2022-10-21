package com.study.junitproject.service;

import com.study.junitproject.domain.Book;
import com.study.junitproject.domain.BookRepository;
import com.study.junitproject.web.dto.response.BookListResponseDto;
import com.study.junitproject.web.dto.response.BookResponseDto;
import com.study.junitproject.web.dto.request.BookSaveRequestDto;
import com.study.junitproject.util.MailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    @DisplayName("책 등록하기")
    void create() {
        //given
        BookSaveRequestDto dto = BookSaveRequestDto.builder()
                .title("testing...")
                .author("tester")
                .build();

        //stub
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        //when
        BookResponseDto bookResponseDto = bookService.create(dto);

        //then
//        assertEquals("testing...", bookResponseDto.getTitle());
//        assertEquals("tester", bookResponseDto.getAuthor());
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    @DisplayName("책 목록 조회하기")
    void getList() {
        //given

        //stub
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "testing1", "tester1"));
        books.add(new Book(2L, "testing2", "tester2"));
        when(bookRepository.findAll()).thenReturn(books);

        //when
        BookListResponseDto bookListResponseDto = bookService.getList();

        //then
        assertThat(bookListResponseDto.getItems().get(0).getTitle()).isEqualTo("testing1");
        assertThat(bookListResponseDto.getItems().get(0).getAuthor()).isEqualTo("tester1");
        assertThat(bookListResponseDto.getItems().get(1).getTitle()).isEqualTo("testing2");
        assertThat(bookListResponseDto.getItems().get(1).getAuthor()).isEqualTo("tester2");
    }

    @Test
    @DisplayName("책 단건 조회하기")
    void get() {
        //given
        Long id = 1L;

        //stub
        Book book = new Book(id, "testing...", "tester");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        //when
        BookResponseDto bookResponseDto = bookService.get(id);

        //then
        assertThat(bookResponseDto.getTitle()).isEqualTo("testing...");
        assertThat(bookResponseDto.getAuthor()).isEqualTo("tester");
    }

    @Test
    @DisplayName("책 수정하기")
    void update() {
        //given
        Long id = 1L;
        BookSaveRequestDto dto = BookSaveRequestDto.builder()
                .title("after testing")
                .author("after tester")
                .build();

        //stub
        Book book = new Book(1L, "before testing", "before tester");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        //when
        BookResponseDto bookResponseDto = bookService.update(id, dto);

        //then
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());
    }
}

