package kr.inhatc.pip.basic.service;

import kr.inhatc.pip.basic.entity.Basic;
import kr.inhatc.pip.basic.repository.BasicRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BasicServiceTest {

    @Autowired
    private BasicService basicService;

    @Autowired
    private BasicRepository basicRepository;

    @AfterEach
    public void atferEach() {
        basicRepository.deleteAll();
    }

    @Test
    void join() {
        Basic basic = Basic.builder().name("spring3").build();

        Long saveId = basicService.join(basic);

        Basic findBasic = basicService.findOne(saveId).get();
        assertThat(basic.getName()).isEqualTo(findBasic.getName());
    }

    @Test
    public void validateDuplicateCheck() {
        Basic basic1 = Basic.builder().name("spring").build();
        Basic basic2 = Basic.builder().name("spring").build();

        basicService.join(basic1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> basicService.join(basic2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findBasic() {
    }

    @Test
    void findOne() {
    }
}