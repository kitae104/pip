package kr.inhatc.pip.board.repository;

import kr.inhatc.pip.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
