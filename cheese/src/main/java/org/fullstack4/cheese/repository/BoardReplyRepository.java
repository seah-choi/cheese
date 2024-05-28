package org.fullstack4.cheese.repository;

import org.fullstack4.cheese.domain.BoardReplyEntity;
import org.fullstack4.cheese.repository.replyPaging.BoardReplyPaging;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReplyEntity, Integer>, BoardReplyPaging {
    List<BoardReplyEntity> findByBbsIdx(int bbs_idx);
    int countAllByBbsIdx(int bbs_idx);
}
