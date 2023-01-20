package com.myboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myboard.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByDeleteYN(Boolean deleteYN, Pageable pageable);
    //Page<Board> findAllByDeleteYN(Specification<Board> spec, Boolean deleteYN, Pageable pageable);
    @Query("select "
            + "distinct b "
            + "from Board b " 
            + "left outer join Comment c on c.board=b "
            + "where "
            + "   (b.title like %:kw% "
            + "   or b.content like %:kw% "
            + "   or b.author.username like %:kw% "
            + "   or c.content like %:kw%) "
            + "   and b.deleteYN = 'N'")
    Page<Board> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
