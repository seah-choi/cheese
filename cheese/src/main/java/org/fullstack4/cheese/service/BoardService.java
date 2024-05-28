package org.fullstack4.cheese.service;

import org.fullstack4.cheese.domain.GoodBbsEntity;
import org.fullstack4.cheese.dto.BoardDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;
import org.fullstack4.cheese.exception.InsufficientStockException;

import java.time.LocalDate;
import java.util.List;


public interface BoardService {
    PageResponseDTO<BoardDTO> bbsListByPage(PageRequestDTO pageRequestDTO);
    BoardDTO view(int bbsIdx);
    void delete(int bbsIdx);
    int modify(BoardDTO boardDTO);
    int regist(BoardDTO boardDTO);
    void share(String myId, String[] userIdList,int bbs_idx) throws InsufficientStockException;
    void addGood(String id, int bbsIdx);
    boolean viewGood(int bbsIdx,String id);
    void removeGood(String id, int bbsIdx);
    List<BoardDTO> mainReadCntList();
    List<BoardDTO> mainGoodList();
    List<BoardDTO> mainBoardList(String bbs_category_code);

}
