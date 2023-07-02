package kr.inhatc.pip.board.service;

import jakarta.transaction.Transactional;
import kr.inhatc.pip.board.dto.BoardDTO;
import kr.inhatc.pip.board.entity.Board;
import kr.inhatc.pip.board.entity.Member;
import kr.inhatc.pip.board.repository.BoardRepository;
import kr.inhatc.pip.board.repository.ReplyRepository;
import kr.inhatc.pip.utils.dto.PageRequestDTO;
import kr.inhatc.pip.utils.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    /**
     * BoardDTO를 Board 엔티티로 변환
     * @param dto
     * @return
     */
    Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        Board entity = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return entity;
    }

    /**
     * Board 엔티티를 BoardDTO로 변환
     * @param board
     * @param member
     * @param replyCount
     * @return
     */
    BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) //int로 처리하도록
                .build();

        return boardDTO;
    }

    /**
     * 새로운 게시판 정보 저장하기 (등록)
     * @param dto
     * @return
     */
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }

    /**
     * 게시판 목록 가져오기
     * @param pageRequestDTO
     * @return PageResultDto<BoardDTO, Object[]> : BoardDTO와 Object[]의 튜플로 구성된 PageResultDto
     */
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info("pageRequestDTO: " + pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO(
                (Board)en[0], (Member)en[1], (Long)en[2]));

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, fn);
    }

    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[])result;                                    // Object[]로 반환되므로
        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);    // 각각의 타입으로 형변환
    }

    @Transactional  // 트랜잭션 처리
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Transactional  // 트랜잭션 처리
    public void modify(BoardDTO boardDTO) {
        Board board = boardRepository.getOne(boardDTO.getBno());   // 영속성 컨텍스트에 의해 select 쿼리가 날라감
        // 변경 감지(더티 체킹)
        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        // 실제 아래 코드는 필요없음
        boardRepository.save(board);    // JPA의 영속성 컨텍스트에 의해 변경감지가 일어나서 update 쿼리가 날라감
    }
}
