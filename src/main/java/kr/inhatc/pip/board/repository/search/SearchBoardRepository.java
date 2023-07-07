package kr.inhatc.pip.board.repository.search;

import kr.inhatc.pip.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    public Board search1();

    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
