package org.fullstack4.cheese.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.domain.BoardEntity;
import org.fullstack4.cheese.domain.GoodBbsEntity;
import org.fullstack4.cheese.dto.BoardDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;
import org.fullstack4.cheese.exception.InsufficientStockException;
import org.fullstack4.cheese.repository.BoardRepository;
import org.fullstack4.cheese.repository.GoodBbsRepository;
import org.fullstack4.cheese.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final GoodBbsRepository goodBbsRepository;
    @Override
    public PageResponseDTO<BoardDTO> bbsListByPage(PageRequestDTO pageRequestDTO) {
        PageRequest pageable;
        if(pageRequestDTO.getSort_type() != null && !pageRequestDTO.getSort_type().isEmpty()) {
            pageable = PageRequest.of(pageRequestDTO.getPage(),
                    pageRequestDTO.getPage_size(),
                    Sort.by(pageRequestDTO.getSort_type()).descending().and(Sort.by("bbsIdx").descending()));
        }else{
            pageable = PageRequest.of(pageRequestDTO.getPage(),
                    pageRequestDTO.getPage_size(),
                    Sort.by("bbsIdx").descending());
        }
        Page<BoardEntity> result = boardRepository.search(pageable,
                pageRequestDTO.getSearch_types(),
                pageRequestDTO.getSearch_word(),
                pageRequestDTO.getSearch_date1(),
                pageRequestDTO.getSearch_date2(),
                pageRequestDTO.getBbsCategoryCode());

        log.info("결과 : " + result.toList());

        List<BoardDTO> boardDTOList = result.toList().stream()
                .map(entity ->modelMapper.map(entity,BoardDTO.class))
                .collect(Collectors.toList());

        PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardDTOList)
                .total_count((int)result.getTotalElements())
                .build();

        return responseDTO;
    }

    @Override
    public PageResponseDTO<BoardDTO> shareBbsListByPage(PageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public BoardDTO view(int bbsIdx) {
        BoardEntity boardEntity = boardRepository.findById(bbsIdx).orElse(null);
        BoardDTO boardDTO = modelMapper.map(boardEntity, BoardDTO.class);

        log.info("boardDTO : {}", boardDTO.toString());
        return boardDTO;
    }

    @Override
    public void delete(int bbsIdx) {
        BoardEntity boardEntity = boardRepository.findById(bbsIdx).orElse(null);
        boardRepository.delete(boardEntity);
    }

    @Override
    public void shareDelete(int idx) {

    }

    @Override
    public int modify(BoardDTO boardDTO) {
        BoardEntity boardEntity = modelMapper.map(boardDTO, BoardEntity.class);
        boardEntity.setModify_date(LocalDateTime.now());
        int idx = boardRepository.save(boardEntity).getBbsIdx();
        return idx;
    }

    @Override
    public int regist(BoardDTO boardDTO) {
        BoardEntity boardEntity = modelMapper.map(boardDTO, BoardEntity.class);
        int idx = boardRepository.save(boardEntity).getBbsIdx();
        return idx;
    }

    @Override
    public void share(String myId, String[] userIdList, int bbs_idx) throws InsufficientStockException {

    }

    @Override
    public void addGood(String id, int bbsIdx) {

    }

    @Override
    public boolean viewGood(int bbsIdx, String id) {
        GoodBbsEntity bbsEntity = goodBbsRepository.findByUserIdAndBbsIdx(id,bbsIdx);
        log.info(bbsEntity);
        if(bbsEntity==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void removeGood(String id, int bbsIdx) {

    }

    @Override
    public List<BoardDTO> shareList(int bbsIdx) {
        return List.of();
    }

    @Override
    public List<BoardDTO> todayList(String userId, LocalDate date) {
        return List.of();
    }

    @Override
    public List<BoardDTO> mainReadCntList() {
        List<BoardEntity> entityList = boardRepository.findAllByOrderByBbsReadCntDesc();
        List<BoardDTO> dtoList = entityList.stream().map(vo-> modelMapper.map(vo, BoardDTO.class))
                .limit(5)
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<BoardDTO> mainGoodList() {
        List<BoardEntity> entityList = boardRepository.findAllByOrderByBbsGoodDesc();
        List<BoardDTO> dtoList = entityList.stream().map(vo-> modelMapper.map(vo, BoardDTO.class))
                .limit(5)
                .collect(Collectors.toList());
        return dtoList;
    }
}
