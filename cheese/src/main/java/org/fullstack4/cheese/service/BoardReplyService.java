package org.fullstack4.cheese.service;

import org.fullstack4.cheese.dto.BoardDTO;
import org.fullstack4.cheese.dto.BoardReplyDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;

public interface BoardReplyService {
    PageResponseDTO<BoardReplyDTO> replyListByPage(PageRequestDTO pageRequestDTO,int bbs_idx);
    int modify(BoardReplyDTO boardreplyDTO);
    int regist(BoardReplyDTO boardreplyDTO);
    void delete(int reply_idx);

}
