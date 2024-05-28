package org.fullstack4.cheese.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.dto.BoardDTO;
import org.fullstack4.cheese.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MainController {
    private final BoardService boardService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){

/*        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String regDate = now.format(formatter);*/

        List<BoardDTO> readCntList = boardService.mainReadCntList();
        List<BoardDTO> goodList = boardService.mainGoodList();
        List<BoardDTO> noticeList = boardService.mainBoardList("bbs02");
        List<BoardDTO> productList = boardService.mainBoardList("bbs01");

        model.addAttribute("readCntList", readCntList);
        model.addAttribute("goodList", goodList);
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("productList",productList);

        log.info("readCntList : {}", readCntList);
        log.info("goodList : {} ", goodList);
        log.info("noticeList : {}", noticeList);
        log.info("productList : {}", productList);


        return "main";
    }


}
