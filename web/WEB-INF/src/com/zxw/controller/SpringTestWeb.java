package com.zxw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SpringTestWeb {
	
	
    @RequestMapping(value="/helloWorld.html", method=RequestMethod.GET)
    @ResponseBody
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        
        return "hello";
    }
	

}
