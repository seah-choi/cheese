package org.fullstack4.cheese.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.domain.BoardReplyEntity;
import org.fullstack4.cheese.dto.BoardDTO;
import org.fullstack4.cheese.dto.BoardReplyDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;
import org.fullstack4.cheese.repository.BoardReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class BoardReplyServiceImpl implements  BoardReplyService {

    private final BoardReplyRepository boardReplyRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<BoardReplyDTO> replyListByPage(PageRequestDTO pageRequestDTO,int bbs_idx) {

        PageRequest pageable = PageRequest.of(pageRequestDTO.getPage(),
                pageRequestDTO.getPage_size(),
                Sort.by("replyIdx").descending());

        Page<BoardReplyEntity> result = boardReplyRepository.paging(pageable,bbs_idx);

        List<BoardReplyDTO> replyDTOList = result.toList().stream()
                .map(entity ->modelMapper.map(entity,BoardReplyDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO<BoardReplyDTO> responseDTO = PageResponseDTO.<BoardReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(replyDTOList)
                .total_count((int)result.getTotalElements())
                .build();

        return responseDTO;
    }

    @Override
    public int modify(BoardReplyDTO boardreplyDTO) {
        BoardReplyEntity boardReplyEntity = modelMapper.map(boardreplyDTO, BoardReplyEntity.class);
        int idx = boardReplyRepository.save(boardReplyEntity).getReplyIdx();
        return idx;
    }

    @Override
    public int regist(BoardReplyDTO boardreplyDTO) {
        BoardReplyEntity boardReplyEntity = modelMapper.map(boardreplyDTO, BoardReplyEntity.class);
        int idx = boardReplyRepository.save(boardReplyEntity).getReplyIdx();
        return idx;
    }

    @Override
    public void delete(int reply_idx) {
        boardReplyRepository.deleteById(reply_idx);
    }
}
