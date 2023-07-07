package kr.inhatc.pip.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import kr.inhatc.pip.board.entity.Board;
import kr.inhatc.pip.board.entity.QBoard;
import kr.inhatc.pip.board.entity.QMember;
import kr.inhatc.pip.board.entity.QReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPAQuery<Board> query = (JPAQuery<Board>) from(board);                  // from절에 Q도메인 클래스를 지정
        query.leftJoin(member).on(board.writer.eq(member));                    // left join으로 member와 board를 조인
        query.leftJoin(reply).on(reply.board.eq(board));                       // left join으로 reply와 board를 조인

        JPQLQuery<Tuple> tuple = query.select(board, member.email, reply.count());   // select절에 필요한 내용을 지정
        tuple.groupBy(board);                                                   // group by절에 board를 지정

        log.info("---------------------------");
        log.info("" + tuple);
        log.info("---------------------------");

        List<Tuple> result = tuple.fetch();

        log.info("" + result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage.................... 수행");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //SELECT b, w, count(r) FROM Board b
        //LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board = b
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null){
            String[] typeArr = type.split("");

            //검색 조건을 작성하기
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t:typeArr) {
                switch (t) {
                    case "t" -> conditionBuilder.or(board.title.contains(keyword));
                    case "w" -> conditionBuilder.or(member.email.contains(keyword));
                    case "c" -> conditionBuilder.or(board.content.contains(keyword));
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);
        tuple.groupBy(board);

        List<Tuple> result = tuple.fetch();

        log.info("result : " + result);


        return null;
    }
}
