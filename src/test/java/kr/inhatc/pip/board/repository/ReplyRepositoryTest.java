package kr.inhatc.pip.board.repository;

import jakarta.transaction.Transactional;
import kr.inhatc.pip.board.entity.Board;
import kr.inhatc.pip.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long bno = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply...." + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    @Transactional
    public void readReply1() {
        Reply reply = replyRepository.findById(100L).get();
        System.out.println(reply);
        System.out.println(reply.getBoard());
    }
}