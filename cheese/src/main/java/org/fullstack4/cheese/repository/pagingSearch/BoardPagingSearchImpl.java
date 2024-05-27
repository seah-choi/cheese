package org.fullstack4.cheese.repository.pagingSearch;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.domain.BoardEntity;
import org.fullstack4.cheese.domain.QBoardEntity;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class BoardPagingSearchImpl extends QuerydslRepositorySupport implements BoardPagingSearch {
    public BoardPagingSearchImpl() {
        super(BoardEntity.class);
    }

    @Override
    public Page<BoardEntity> search(Pageable pageable, String types[], String search_keyword, LocalDate Search_date1, LocalDate Search_date2,String bbsCategoryCode) {
        log.info("==============================");
        log.info("BoardSearchImpl >> search START");
        log.info("Search_date1 = " + Search_date1);
        log.info("Search_date2 = " + Search_date2);
        QBoardEntity qBoard = QBoardEntity.boardEntity; // QBoardEntity 객체 생성
        // SELECT ~~ FROM QBoardEntity <- tbl_board // tbl_board에서 SELECT 해오라는 뜻과 같음
        JPQLQuery<BoardEntity> query = from(qBoard);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if ((types != null && types.length > 0) && (search_keyword != null && search_keyword.length() > 0)) {
            // type : t(제목), c(내용), u(사용자아이디)
            for (String type : types) {
                switch (type) {
                    case "t" : booleanBuilder.or(qBoard.bbsTitle.like("%" + search_keyword + "%"));
                        break;
                    case "c" : booleanBuilder.or(qBoard.bbsContent.like("%" + search_keyword + "%"));
                        break;
                }
            }
        }
        if(Search_date1 !=null && Search_date2 != null){
            booleanBuilder.or(qBoard.regDate.between(Search_date1.atStartOfDay(), Search_date2.atStartOfDay()));
        }
        query.where(qBoard.bbsCategoryCode.eq(bbsCategoryCode));
        query.where(booleanBuilder);
        query.where(qBoard.bbsIdx.gt(0));


        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);

        List<BoardEntity> boards = query.fetch();
        int total = (int)query.fetchCount();

        log.info("boards : {}", boards);
        log.info("total : {}", total);


        log.info("BoardSearchImpl >> search END");
        log.info("==============================");

        return new PageImpl<>(boards, pageable, (long)total);
    }

}
