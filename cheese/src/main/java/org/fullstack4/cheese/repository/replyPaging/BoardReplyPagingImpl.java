package org.fullstack4.cheese.repository.replyPaging;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;

import org.fullstack4.cheese.domain.BoardReplyEntity;
import org.fullstack4.cheese.domain.QBoardReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardReplyPagingImpl extends QuerydslRepositorySupport implements BoardReplyPaging {

    public BoardReplyPagingImpl() {
        super(BoardReplyEntity.class);
    }

    @Override
    public Page<BoardReplyEntity> paging(Pageable pageable, int bbs_idx) {

        QBoardReplyEntity qBoardReplyEntity = QBoardReplyEntity.boardReplyEntity;
        JPQLQuery<BoardReplyEntity> query = from(qBoardReplyEntity);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        query.where(qBoardReplyEntity.bbsIdx.eq(bbs_idx));

        this.getQuerydsl().applyPagination(pageable, query);

        List<BoardReplyEntity> qBoardReplyEntities = query.fetch();
        int total = (int)query.fetchCount();


        return new PageImpl<>(qBoardReplyEntities, pageable, (long)total);
    }
}
