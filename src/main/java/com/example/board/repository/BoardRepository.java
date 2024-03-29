package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> , SearchBoardRepository {

    // 한개의 로우(Object) 내에 Object[]로 나옴
    @Query("select b , w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b , r from Board b LEFT JOIN Reply r ON r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReplyUsingLeftOuterJoin(@Param("bno") Long bno);

    @Query("select b , r from Board b JOIN Reply r ON r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReplyUsingInnerJoin(@Param("bno") Long bno);

    @Query( value = "select b, w , count(r) from Board b left join b.writer w " +
            "left join Reply r on r.board = b group by b", countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCountUsingLeftJoin(Pageable pageable);// 목록 화면에 필요한 데이터

    @Query( value = "select b, w , count(r) from Board b join b.writer w " +
            " join Reply r on r.board = b group by b", countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCountUsingInnerJoin(Pageable pageable);// 목록 화면에 필요한 데이터

    @Query("select b, w, count(r) from Board b left join b.writer w left outer join Reply r on r.board = b where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
