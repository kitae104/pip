package kr.inhatc.pip.board.repository.search;

import com.querydsl.jpa.impl.JPAQuery;
import kr.inhatc.pip.board.entity.Board;
import kr.inhatc.pip.board.entity.QBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Slf4j
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("search1.................... 수행");

        QBoard board = QBoard.board;

        JPAQuery<Board> query = (JPAQuery<Board>) from(board);                  // from절에 Q도메인 클래스를 지정

        log.info("" + query.select(board).where(board.bno.eq(2L)));

        List<Board> result = query.fetch();

        log.info("result : " + result);

        return null;
    }
}
