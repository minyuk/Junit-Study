package com.study.junitproject.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest //DB 외 관련된 컴포넌트로만 테스트 메모리에 로딩
public class BookRepositoryTest {

    @Autowired //DI
    private BookRepository bookRepository;

    //@BeforeAll //테스트 시작전에 한번만 실행
    @BeforeEach // 각 테스트 시작전에 한번씩 실행
    public void createBook() {
        Book book = Book.builder()
                .title("Junit")
                .author("겟인데어")
                .build();

        Book bookPS = bookRepository.save(book);
    }
    //가정 1 : [ createBook() + 1 save ] (T) , [ createBook() + 2 getList ] (T) (검증 완료)
    //가정 2 : [ createBook() + 1 save + createBook() + 2 getList ] (T) (검증 실패)

    //1. 책 등록
    @Test
    void save() {
        //given (데이터 준비)
        String title = "Junit5";
        String author = "tester";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        //when (테스트 실행)
        Book bookPS = bookRepository.save(book); //DB에 저장 (primary key 생성 = id 생성 완료) -> save 메서드가 DB에 저장된 Book을 리턴 (DB 데이터와 동기화 된 데이터)

        //then (검증)
        assertEquals("Junit5", bookPS.getTitle());
        assertEquals("tester", bookPS.getAuthor());

    }//트랜잭션 종료(저장된 데이터를 초기화)

    //2. 책 목록 보기
    @Test
    void getList() {
        //given
        String title = "Junit";
        String author = "겟인데어";

        //when
        List<Book> booksPS = bookRepository.findAll();

        //then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    }

    //3. 책 한건 보기
    @Sql("classpath:db/tableInit.sql") //ID를 테스트 앞에는 붙여주자!
    @Test
    void get() {
        //given
        String title = "Junit";
        String author = "겟인데어";

        //when
        Book bookPS = bookRepository.findById(1L).orElseThrow();

        //then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    //4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    void delete() {
        //given
        Long id = 1L;

        //when
        bookRepository.deleteById(id);

        //then
        assertFalse(bookRepository.findById(id).isPresent());
    }

    //5. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    void update() {
        //given
        Long id = 1L;
        String title = "junit5";
        String author = "minyuk";
        Book book = new Book(id, title, author);

        //when
        Book bookPS = bookRepository.save(book);

        //then
        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }
}
