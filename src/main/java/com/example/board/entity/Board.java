package com.example.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = "writer") //@ToString 은 항상 exclude
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 명시적으로 Lazy 로딩 지정
    private Member writer; // 연관관계 지정
    // db 에 생길때 writer_email VARCHAR(255)
    // board 는 많고 member 는 하나다
    // member 하나가 board(게시물)을 여러개 쓸수 있다.

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

}
