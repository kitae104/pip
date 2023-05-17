package kr.inhatc.pip.basic.repository;

import kr.inhatc.pip.basic.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
