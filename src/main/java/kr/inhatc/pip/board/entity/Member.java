package kr.inhatc.pip.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kr.inhatc.pip.utils.entity.BaseEntity;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_email")
    private String email;

    private String password;

    private String name;

}