package com.hs.takeover.contoller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListController {
    @GetMapping("/list")
    public String index(ModelMap map) {
    	map.addAttribute("lists", new ArrayList());
        return "list";
    }

}
