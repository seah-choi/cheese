package org.fullstack4.cheese.repository;

import org.fullstack4.cheese.domain.BoardEntity;
import org.fullstack4.cheese.repository.pagingSearch.BoardPagingSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, BoardPagingSearch {
    List<BoardEntity> findAllByOrderByBbsGoodDesc();
    List<BoardEntity> findAllByOrderByBbsReadCntDesc();
}
