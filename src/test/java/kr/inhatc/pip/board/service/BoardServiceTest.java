package kr.inhatc.pip.board.service;

import kr.inhatc.pip.board.dto.BoardDTO;
import kr.inhatc.pip.utils.dto.PageRequestDTO;
import kr.inhatc.pip.utils.dto.PageResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("등록 테스트")
    void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")
                .build();
        Long bno = boardService.register(dto);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDto = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> resultDto = boardService.getList(pageRequestDto);

        for(BoardDTO boardDTO : resultDto.getDtoList()){
            System.out.println(boardDTO);
        }
    }

    @Test
    @DisplayName("게시글 조회")
    public void testGet(){
        Long bno = 98L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    @Test
    @DisplayName("게시글 번호에 의한 답글 삭제")
    public void testRemoveWithReplies(){
        Long bno = 1L;
        boardService.removeWithReplies(bno);
    }

    @Test
    @DisplayName("게시글 수정")
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("제목 수정")
                .content("내용 수정")
                .build();
        boardService.modify(boardDTO);
    }
}