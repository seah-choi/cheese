package org.fullstack4.cheese.service;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.domain.BoardReplyEntity;
import org.fullstack4.cheese.dto.BoardReplyDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;
import org.fullstack4.cheese.repository.BoardReplyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@SpringBootTest
public class HyunBeenTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private BoardReplyRepository boardReplyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testConnection() throws SQLException {
        @Cleanup
        Connection conn = dataSource.getConnection();
        log.info("============================");
        log.info("conn : " + conn);
        log.info("============================");

        Assertions.assertNotNull(conn);
    }

    @Test
    public void testBoardReply(){
//        for(int i=1;i<=31; i++) {
//            BoardReplyDTO boardreplyDTO = BoardReplyDTO.builder()
//                    .bbsIdx(178)
//                    .userId("test0")
//                    .replyContent("댓글 테스트"+i)
//                    .build();
//
//            BoardReplyEntity boardReplyEntity = modelMapper.map(boardreplyDTO, BoardReplyEntity.class);
//            int idx = boardReplyRepository.save(boardReplyEntity).getReplyIdx();
//        }

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageRequest pageable = PageRequest.of(pageRequestDTO.getPage(),
                pageRequestDTO.getPage_size(),
                Sort.by("replyIdx").descending());

        Page<BoardReplyEntity> result = boardReplyRepository.paging(pageable,178);

        List<BoardReplyDTO> replyDTOList = result.toList().stream()
                .map(entity ->modelMapper.map(entity,BoardReplyDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO<BoardReplyDTO> responseDTO = PageResponseDTO.<BoardReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(replyDTOList)
                .total_count((int)result.getTotalElements())
                .build();

        log.info("responseDTO:{}", responseDTO);
    }
}
