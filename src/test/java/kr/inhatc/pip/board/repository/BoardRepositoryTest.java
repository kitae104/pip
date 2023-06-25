package kr.inhatc.pip.board.repository;

import kr.inhatc.pip.board.entity.Board;
import kr.inhatc.pip.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder().email("user"+i +"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content...." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);

        });
    }

    @Test
    @Transactional
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(1L);
        Board board = result.get();
        System.out.println(board);
        System.out.println(board.getWriter());
    }
}