package org.fullstack4.cheese.repository.replyPaging;

import org.fullstack4.cheese.domain.BoardReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardReplyPaging {
    Page<BoardReplyEntity> paging(Pageable pageable, int bbs_idx);
}
