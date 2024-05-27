package org.fullstack4.cheese.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/join")
    public void joinGET(){

    }

    @GetMapping("/login")
    public void loginGET(){

    }

    @GetMapping("/modify")
    public void modifyGET(){

    }
}
