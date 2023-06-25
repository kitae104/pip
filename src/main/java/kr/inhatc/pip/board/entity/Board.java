package kr.inhatc.pip.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")               // exclude = "writer" : writer를 제외하고 출력
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_bno")
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)                  // 다대일 관계
    @JoinColumn(name = "member_email")
    private Member writer;
}
