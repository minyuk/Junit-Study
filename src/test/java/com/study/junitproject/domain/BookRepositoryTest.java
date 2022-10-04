package com.study.junitproject.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest //DB 외 관련된 컴포넌트로만 테스트 메모리에 로딩
public class BookRepositoryTest {

    @Autowired //DI
    private BookRepository bookRepository;

    //1. 책 등록
    @Test
    void save() {

        //given (데이터 준비)
        Book book = Book.builder()
                .title("Junit5")
                .author("tester")
                .build();

        //when (테스트 실행)
        Book bookPS = bookRepository.save(book); //DB에 저장 (primary key 생성 = id 생성 완료) -> save 메서드가 DB에 저장된 Book을 리턴 (DB 데이터와 동기화 된 데이터)

        //then (검증)
        assertEquals("Junit5", bookPS.getTitle());
        assertEquals("tester", bookPS.getAuthor());

    }
    //2. 책 목록 보기
    //3. 책 한건 보기
    //4. 책 수정
    //5. 책 삭제
}
