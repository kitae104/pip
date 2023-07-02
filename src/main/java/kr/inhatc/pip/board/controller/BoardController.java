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

}
