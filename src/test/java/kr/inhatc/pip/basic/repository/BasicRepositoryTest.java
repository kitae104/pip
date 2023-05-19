package kr.inhatc.pip.basic.repository;

import kr.inhatc.pip.basic.entity.Basic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BasicRepositoryTest {

    @Autowired
    private BasicRepository basicRepository;

    @AfterEach
    public void atferEach() {
        basicRepository.deleteAll();
    }

    @Test
    public void saveTest() {
        Basic basic = Basic.builder().name("spring").build();
        basicRepository.save(basic);

        Basic result = basicRepository.findById(basic.getId()).get();
        assertEquals(result.getId(), basic.getId());
    }
    @Test
    public void findByNameTest() {
        Basic basic = Basic.builder().name("spring").build();
        basicRepository.save(basic);

        Basic basic1 = Basic.builder().name("spring2").build();
        basicRepository.save(basic1);

        Basic result = basicRepository.findByName("spring2").get();
        assertThat(result.getName()).isEqualTo("spring2");
    }


}