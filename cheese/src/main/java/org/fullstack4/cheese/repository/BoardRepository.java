package org.fullstack4.cheese.repository;

import org.fullstack4.cheese.domain.BoardEntity;
import org.fullstack4.cheese.repository.pagingSearch.BoardPagingSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>, BoardPagingSearch {
}
