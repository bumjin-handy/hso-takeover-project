package com.hs.takeover.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String index(ModelMap map) {
    	map.addAttribute("country", "KOREA");
        return "index";
    }

}
