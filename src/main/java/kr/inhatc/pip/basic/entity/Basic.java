package kr.inhatc.pip.basic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Basic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basic_id")
    private Long id;
    private String name;
    private int age;
}
