package org.fullstack4.cheese.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/list")
    public void listGET(){

    }

    @GetMapping("/view")
    public void viewGET(){

    }

    @GetMapping("/regist")
    public void registGET(){

    }

    @GetMapping("/modify")
    public void modifyGET(){

    }

    @GetMapping("/freeList")
    public void freeListGET(){

    }

    @GetMapping("/freeView")
    public void freeViewGET(){

    }
}
