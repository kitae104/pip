package kr.inhatc.pip.utils.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    //DTO리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;

    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지 번호  목록
    private List<Integer> pageList;


    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList()); // 엔티티를 DTO로 변환
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;               // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();                     // 페이지 사이즈
        //temp end page
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;      // 올림처리
        start = tempEnd - 9;                                    // 시작 페이지 번호
        prev = start > 1;                                       // 이전 버튼 생성 여부
        end = totalPage > tempEnd ? tempEnd : totalPage;        // 진짜 끝 페이지 번호
        next = totalPage > tempEnd;                             // 다음 버튼 생성 여부
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());      // 페이지 번호 목록
    }
}
