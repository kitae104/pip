package kr.inhatc.pip.board.entity;

import jakarta.persistence.*;
import kr.inhatc.pip.utils.entity.BaseEntity;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")               // exclude = "board" : board를 제외하고 출력
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_rno")
    private Long rno;

    private String text;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)                   // 다대일 관계
    @JoinColumn(name = "board_bno")
    private Board board;
}
