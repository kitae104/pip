package kr.inhatc.pip.board.repository;

import kr.inhatc.pip.board.entity.Board;
import kr.inhatc.pip.board.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    // 목록 화면에서 게시글의 정보와 함께 작성자를 같이 가져오기
    // left join을 이용하여 Board와 Member를 가져옴
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);              // 조회 결과는 다양한 내용이 포함되어 Object에 담아옴


    // 특정 게시물과 해당 게시물에 속한 댓글들을 조회하는 경우
    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);       // 조회 결과는 다양한 내용이 포함되어 Object 배열로 된 리스트에 담아옴

    // 목록 화면에서 게시글의 정보, 회원 정보와 함께 댓글의 수를 가져오기
    @Query(value = "SELECT b, w, count(r)" +                        // board, member, reply의 수를 가져옴
            " FROM Board b " +                                      // board를 기준으로 가져옴
            " LEFT JOIN b.writer w " +                              // board와 member를 left join
            " LEFT JOIN Reply r ON r.board = b " +                  // board와 reply를 left join
            " GROUP BY b",                                          // board를 기준으로 group by
            countQuery = "SELECT count(b) FROM Board b")            // board의 수를 가져옴
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);       // 목록 화면에서 페이징 처리를 위해 Pageable을 파라미터로 지정

    // 게시글의 번호에 대한 조회 화면 - 게시글의 정보와 회원 정보, 댓글의 정보를 가져오기
    @Query("SELECT b, w, count(r) " +                               // board, member, replay의 수를 가져옴
           "FROM Board b LEFT JOIN b.writer w " +                   // board와 member를 left join
            "LEFT OUTER JOIN Reply r ON r.board = b " +             // board와 reply를 left outer join
            "WHERE b.bno = :bno")                                   // board의 번호에 해당하는 정보를 가져옴
    Object getBoardByBno(@Param("bno") Long bno);                   // 조회 결과는 다양한 내용이 포함되어 Object에 담아옴
}
