package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying // update, delete 를 실행하기 위해서는 @Modifying 어노테이션을 같이 추가해야한다.
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(Long bno);

    //게시물로 댓글 목록 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
