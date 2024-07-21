package com.hs.takeover.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController {
    @RequestMapping("/")
    public String index(ModelMap map) {
    	log.trace("Trace Level 테스트");
    	log.debug("DEBUG Level 테스트");
    	log.info("INFO Level 테스트");
    	log.warn("Warn Level 테스트");
    	log.error("ERROR Level 테스트");
    	
		try {
			InetAddress ipAddress = InetAddress.getLocalHost();
			String hostname = ipAddress.getHostName();
			String ip = ipAddress.getHostAddress();
			log.debug("hostname {}", hostname);
			log.debug("ip {}", ip);
			map.addAttribute("hostname",  hostname);
			map.addAttribute("ip", ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			log.error("Error : ", e.getMessage());
		}
	
        return "index";
    }

}
