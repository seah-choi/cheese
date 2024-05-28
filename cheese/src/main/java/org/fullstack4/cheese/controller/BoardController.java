package org.fullstack4.cheese.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.common.FileUtil;
import org.fullstack4.cheese.dto.BoardDTO;
import org.fullstack4.cheese.dto.BoardReplyDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;
import org.fullstack4.cheese.service.BoardReplyService;
import org.fullstack4.cheese.service.BoardService;
import org.fullstack4.cheese.service.BoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final BoardReplyService boardReplyService;

    @GetMapping("/list")
    public void listGET(@Valid PageRequestDTO pageRequestDTO, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String user_id = session.getAttribute("user_id") == null? "":session.getAttribute("user_id").toString();
        pageRequestDTO.setUserId(user_id);
        PageResponseDTO<BoardDTO> responseDTO = boardService.bbsListByPage(pageRequestDTO);
        log.info("responseDTO : {}", responseDTO);
        String sort = responseDTO.getSort_type() == null?"":"&sort_type="+responseDTO.getSort_type();
        model.addAttribute("sort", sort);
        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping("/view")
    public void viewGET(@RequestParam int bbsIdx, Model model,HttpServletRequest req,PageRequestDTO pageRequestDTO){
        HttpSession session = req.getSession();
        String id = session.getAttribute("user_id") == null? "":session.getAttribute("user_id").toString();

        BoardDTO boardDTO = boardService.view(bbsIdx);
        boolean goodcheck = boardService.viewGood(bbsIdx,id);

        int replyCnt = boardReplyService.replyCnt(bbsIdx);




        PageResponseDTO<BoardReplyDTO> responseDTO = boardReplyService.replyListByPage(pageRequestDTO,bbsIdx);

        log.info("responseDTO : {}", responseDTO);
        log.info("goodcheck : {}", goodcheck);
        log.info("boardDTO:{}", boardDTO.toString());

        model.addAttribute("replyCnt", replyCnt);
        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("goodcheck", goodcheck);
    }

    @GetMapping("/regist")
    public void registGET(@RequestParam String bbsCategoryCode,
                          Model model){
        log.info(bbsCategoryCode);
        model.addAttribute("bbsCategoryCode",bbsCategoryCode);
    }

    @PostMapping("/regist")
    public String PostBbsRegist(BoardDTO boardDTO,
                                Model model,
                                MultipartFile bbsFile1,
                                HttpServletRequest req){


        String directory= "D:\\cheese\\cheese\\src\\main\\resources\\static\\upload";
        HttpSession session = req.getSession();
        boardDTO.setUserId(session.getAttribute("user_id").toString());

        log.info("bbs_file1 이름:"+bbsFile1);
        if(bbsFile1 !=null && !bbsFile1.isEmpty()) {
            Map<String, String> map = FileUtil.FileUpload(bbsFile1, directory);
            boardDTO.setBbsFile(map.get("newName").toString());
            boardDTO.setFileorgname(map.get("orgName").toString());
        }

        log.info("boardDTO 이름:"+boardDTO.toString());
        int idx = boardService.regist(boardDTO);
        if(idx>0) {
            return "redirect:/board/view?bbsIdx=" + idx;
        }else{
            return "redirect:/board/modify?bbsIdx="+boardDTO.getBbsIdx();
        }
    }

    @GetMapping("/modify")
    public void modifyGET(@RequestParam int bbsIdx, Model model){
        BoardDTO boardDTO = boardService.view(bbsIdx);
        model.addAttribute("boardDTO", boardDTO);
    }

    @PostMapping("/modify")
    public String PostBbsModify(BoardDTO boardDTO, Model model,MultipartFile bbsFile1,HttpServletRequest req){
        HttpSession session = req.getSession();
        String directory= "D:\\cheese\\cheese\\src\\main\\resources\\static\\upload";
        boardDTO.setUserId(session.getAttribute("user_id").toString());

        boolean filedel = req.getParameter("fileDel") == null?false:true;


        if (bbsFile1 != null && !bbsFile1.isEmpty()) {
            Map<String, String> map = FileUtil.FileUpload(bbsFile1, directory);
            boardDTO.setBbsFile(map.get("newName").toString());
            boardDTO.setFileorgname(map.get("orgName").toString());
        }else{
            boardDTO.setBbsFile(req.getParameter("orgFileName"));
            boardDTO.setFileorgname(req.getParameter("orgSaveFileName"));
        }


        int idx =boardService.modify(boardDTO);
        log.info("idx:=" + idx);
        if(idx == boardDTO.getBbsIdx()) {
            return "redirect:/board/view?bbsIdx=" + idx;
        }else{
            return "redirect:/board/modify?bbsIdx="+boardDTO.getBbsIdx();
        }
    }

    @RequestMapping(value = "/delete.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String bbsDelete(@RequestParam HashMap<String, Object> map) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        int idx = Integer.parseInt(map.get("idx").toString());
        boardService.delete(idx);

        return new Gson().toJson(resultMap);
    }

    @RequestMapping(value = "/replyregist.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String replyRegist(@RequestParam HashMap<String, Object> map,HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        HttpSession session = req.getSession();

        String replyContent = req.getParameter("replyContent");
        String user_id = session.getAttribute("user_id").toString();
        int bbs_idx = Integer.parseInt(req.getParameter("bbsIdx"));


        BoardReplyDTO boardReplyDTO = BoardReplyDTO.builder()
                .bbsIdx(bbs_idx)
                .userId(user_id)
                .replyContent(replyContent)
                .build();

        log.info(boardReplyDTO);
        int result = boardReplyService.regist(boardReplyDTO);
        if(result>0){
            resultMap.put("result","success");
        }else{
            resultMap.put("result","fail");
        }

        return new Gson().toJson(resultMap);
    }
}
