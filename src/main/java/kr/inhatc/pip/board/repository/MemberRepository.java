package kr.inhatc.pip.board.repository;

import kr.inhatc.pip.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
