package com.example.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    // db 에 생길때 board_bno BIGINT(20)
    // reply 는 많고 board 는 하나다
    // reply(댓글) 은 board 하나에 여러개를 달수 있다.

}
