package kr.inhatc.pip.basic.repository;

import kr.inhatc.pip.basic.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveTest() {
        Member member = Member.builder().name("spring").build();
        memberRepository.save(member);

        Member result = memberRepository.findById(member.getId()).get();
        assertEquals(result.getId(), member.getId());
    }
}