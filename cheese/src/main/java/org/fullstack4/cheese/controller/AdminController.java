package org.fullstack4.cheese.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/regist")
    public void registPOST(){

    }

    @GetMapping("/modify")
    public void modifyGET(){

    }

    @PostMapping("/modify")
    public void modifyPOST(){

    }

    @GetMapping("/freeList")
    public void freeListGET(){

    }

    @GetMapping("/freeView")
    public void freeViewGET(){

    }

    @PostMapping("/delete")
    public void deletePOST(){

    }
}
