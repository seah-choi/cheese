package org.fullstack4.cheese.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.common.CookieUtil;
import org.fullstack4.cheese.dto.MemberDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;
import org.fullstack4.cheese.exception.InsufficientStockException;
import org.fullstack4.cheese.repository.MemberRepository;
import org.fullstack4.cheese.service.EmailService;
import org.fullstack4.cheese.service.MemberService;
import org.fullstack4.cheese.service.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberServiceImpl;
    @GetMapping("/join")
    public void joinGET(){

    }

    @GetMapping("/login")
    public void loginGET(){

    }

    @PostMapping("/join")
    public String Postjoin(MemberDTO memberDTO){
        try {
            memberServiceImpl.join(memberDTO);
            return "redirect:/member/login";
        }catch(Exception e){
            return e.getMessage();
        }
    }

    //////////////////////////////////////////////////////회원 가입////////////////////////////////////////////////

    @RequestMapping(value = "/login.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String Login(@RequestParam HashMap<String, Object> map, HttpServletRequest req,HttpServletResponse resp) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        String id = req.getParameter("user_id").trim();
        String pwd = req.getParameter("pwd").trim();
        boolean save_id = req.getParameter("save_id") ==null?false:true;
        try {
            if (memberServiceImpl.login(id, pwd, req)) {
                if(save_id){
                    CookieUtil.setCookies(resp,"","",50000,"user_id",id);
                }else{
                    CookieUtil.setDeleteCookie(resp,"user_id");
                }
                resultMap.put("result", "success");
                resultMap.put("msg","성공적으로 로그인되었습니다.");
            } else {
                resultMap.put("result", "false");
                resultMap.put("msg","비밀번호가 틀렸습니다.");
            }
        }catch (InsufficientStockException e){
            resultMap.put("result", "false");
            resultMap.put("msg", e.getMessage());
        }
        return new Gson().toJson(resultMap);
    }

    @RequestMapping(value = "/idCheck.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkId(@RequestParam HashMap<String, Object> map, HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();
        try {
            int count = memberServiceImpl.checkId(map.get("memberId1").toString());

            if (count > 0) {
                resultMap.put("result", "fail");
                resultMap.put("msg", "이미 존재하는 아이디입니다.");
            } else {
                resultMap.put("result", "success");
                resultMap.put("msg", "사용하실 수 있는 아이디입니다.");
            }
        }catch (InsufficientStockException e){
            resultMap.put("result", "fail");
            resultMap.put("msg", e.getMessage());
        }

        return new Gson().toJson(resultMap);
    }

    @RequestMapping(value = "/emailCheck.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkEmail(@RequestParam HashMap<String, Object> map, HttpServletRequest req) throws Exception{
        HashMap<String, Object> resultMap = new HashMap<>();

        try {
            int count = memberServiceImpl.checkEmail(map.get("email1").toString(), map.get("email2").toString());

            if (count > 0) {
                resultMap.put("result", "fail");
                resultMap.put("msg", "이미 존재하는 이메일입니다.");
            } else {
                resultMap.put("result", "success");
                resultMap.put("msg", "사용하실 수 있는 이메일입니다.");
            }
        }catch(InsufficientStockException e){
            resultMap.put("result", "fail");
            resultMap.put("msg", e.getMessage());
        }

        return new Gson().toJson(resultMap);

    }


    ////////////////////////////////////////////////////////////////////////

    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.invalidate();
        return "redirect:/member/login";
    }

    @GetMapping("/modify")
    public void modifyGET(){

    }

    @GetMapping("/mypage")
    public void mypageGET(){

    }
}
