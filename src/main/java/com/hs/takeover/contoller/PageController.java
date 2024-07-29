package com.hs.takeover.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class PageController {
    @RequestMapping("/")
    public String index(ModelMap map) {
        return "index";
    }
    
    @RequestMapping("/log")
    public String log(ModelMap map) {
    	log.trace("Trace Level 테스트");
    	log.debug("DEBUG Level 테스트");
    	log.info("INFO Level 테스트");
    	log.warn("Warn Level 테스트");
    	log.error("ERROR Level 테스트");
    	
        return "index";
    }

}
