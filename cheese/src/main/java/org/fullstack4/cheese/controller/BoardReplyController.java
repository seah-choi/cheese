package org.fullstack4.cheese.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.dto.BoardReplyDTO;
import org.fullstack4.cheese.service.BoardReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/bbsReply")
public class BoardReplyController {

    private final BoardReplyService boardReplyService;

    @PostMapping("/regist")
    public String regist(BoardReplyDTO replyDTO, Model model) {
        log.info(replyDTO);
        int result = boardReplyService.regist(replyDTO);


        return "redirect:/board/view?bbsIdx=" + replyDTO.getBbsIdx();
    }

    @RequestMapping(value = "/delete.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String replyDelete(@RequestParam HashMap<String, Object> map) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        int idx = Integer.parseInt(map.get("idx").toString());

        boardReplyService.delete(idx);
        resultMap.put("result", "success");

        return new Gson().toJson(resultMap);
    }
}
