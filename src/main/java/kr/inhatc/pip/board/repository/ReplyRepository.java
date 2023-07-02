package kr.inhatc.pip.board.repository;

import kr.inhatc.pip.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

     /**
      * 보드 번호에 의해서 삭제하기 <br/>
      * 한번에 삭제
      * @param bno
      */
     @Modifying     // update, delete 쿼리를 실행하기 위해서 필요
     @Query("DELETE FROM Reply r WHERE r.board.bno =:bno ")
     void deleteByBno(Long bno);
}
