package com.study.junitproject.service;

import com.study.junitproject.domain.Book;
import com.study.junitproject.domain.BookRepository;
import com.study.junitproject.dto.BookResponseDto;
import com.study.junitproject.dto.BookSaveRequestDto;
import com.study.junitproject.util.MailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setTitle("testing...");
        dto.setAuthor("tester");

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
        List<BookResponseDto> bookResponseDtoList = bookService.getList();

        //then
        assertThat(bookResponseDtoList.get(0).getTitle()).isEqualTo("testing1");
        assertThat(bookResponseDtoList.get(0).getAuthor()).isEqualTo("tester1");
        assertThat(bookResponseDtoList.get(1).getTitle()).isEqualTo("testing2");
        assertThat(bookResponseDtoList.get(1).getAuthor()).isEqualTo("tester2");
    }
}
