package kr.inhatc.pip.board.repository;

import kr.inhatc.pip.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
