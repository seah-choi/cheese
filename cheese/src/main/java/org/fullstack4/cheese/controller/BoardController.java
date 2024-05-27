package org.fullstack4.cheese.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.dto.BoardDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;
import org.fullstack4.cheese.service.BoardService;
import org.fullstack4.cheese.service.BoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void listGET(@Valid PageRequestDTO pageRequestDTO, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String user_id = session.getAttribute("user_id").toString();
        pageRequestDTO.setUserId(user_id);
        PageResponseDTO<BoardDTO> responseDTO = boardService.bbsListByPage(pageRequestDTO);
        log.info("responseDTO : {}", responseDTO);
        String sort = responseDTO.getSort_type() == null?"":"&sort_type="+responseDTO.getSort_type();
        model.addAttribute("sort", sort);
        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping("/view")
    public void viewGET(@RequestParam int bbsIdx, Model model,HttpServletRequest req){
        HttpSession session = req.getSession();
        String id = session.getAttribute("user_id").toString();
        BoardDTO boardDTO = boardService.view(bbsIdx);
        boolean goodcheck = boardService.viewGood(bbsIdx,id);
        log.info("goodcheck : {}", goodcheck);
        log.info("boardDTO:{}",boardDTO.toString());
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("goodcheck", goodcheck);
    }

    @GetMapping("/regist")
    public void registGET(){

    }

    @GetMapping("/modify")
    public void modifyGET(){

    }
}
