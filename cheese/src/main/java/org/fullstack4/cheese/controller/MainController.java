package org.fullstack4.cheese.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MainController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "main";
    }
}
