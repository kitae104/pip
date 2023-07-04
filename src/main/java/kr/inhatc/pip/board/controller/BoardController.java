package kr.inhatc.pip.board.controller;

import kr.inhatc.pip.board.dto.BoardDTO;
import kr.inhatc.pip.board.service.BoardService;
import kr.inhatc.pip.utils.dto.PageRequestDTO;
import kr.inhatc.pip.utils.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;    // final로 선언된 멤버 변수는 반드시 생성자에서 초기화해야 함

    @GetMapping("/list")
    public void list(@ModelAttribute PageRequestDTO pageRequestDto, Model model) {
        log.info("게시판 리스트 ........" + pageRequestDto);
        PageResultDTO<BoardDTO, Object[]> list = boardService.getList(pageRequestDto);
        model.addAttribute("result", list);
    }

    @GetMapping("/register")
    public void register() {
        log.info("regiser get...");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);    //새로 추가된 엔티티의 번호
        Long bno = boardService.register(dto);
        log.info("BNO: " + bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model) {
        log.info("bno: " + bno);
        BoardDTO boardDTO = boardService.get(bno);
        log.info("dto: " + boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){


        log.info("post modify.........................................");
        log.info("dto: " + dto);

        boardService.modify(dto);                   // 수정된 게시글의 번호

        // addFlashAttribute()는 일회성으로 데이터를 전달할 때 사용 - URL에 데이터가 노출되지 않음
        // 반면에 addAttribute()는 URL에 데이터가 노출됨
        redirectAttributes.addAttribute("page",requestDTO.getPage());           // 페이지 번호
        redirectAttributes.addAttribute("type",requestDTO.getType());           // 검색 조건
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());     // 검색 키워드

        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/board/read";

    }


    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes) {
        log.info("bno: " + bno);
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }
}
