package com.study.junitproject.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest //DB 외 관련된 컴포넌트로만 테스트 메모리에 로딩
public class BookRepositoryTest {

    @Autowired //DI
    private BookRepository bookRepository;

    //1. 책 등록
    @Test
    void save() {
        System.out.println("책등록_test_실행");
    }
    //2. 책 목록 보기
    //3. 책 한건 보기
    //4. 책 수정
    //5. 책 삭제
}
