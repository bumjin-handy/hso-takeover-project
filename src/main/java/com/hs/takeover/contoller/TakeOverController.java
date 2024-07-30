package com.hs.takeover.contoller;

import com.hs.takeover.domain.TakeOver;
import com.hs.takeover.service.TakeOverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
public class TakeOverController {
	private final TakeOverService takeOverService;

	@Autowired
	public TakeOverController(TakeOverService takeOverService) {
		this.takeOverService = takeOverService;
	}
	//인계목록
	//인계 기능: 인계자가 인계함에서 인계 신청→ 인계인수서 작성→             
    //인수자 및 입회자 선택→ 기본정보 및 업무현황 등록→ 인수요청
	@GetMapping("/handover/list")
    public String handOverList(Model model) {
		List<TakeOver> handOverList = takeOverService.getList();
		log.debug("handOverList size:"+handOverList.size());
		/*
		 * for (TakeOver takeOver : handOverList) { log.debug(takeOver.toString()); }
		 */
	    model.addAttribute("list", handOverList);
        return "handover_list";
    }
	
	//인수목록
	//인수 기능: 인수자가 인수함에서 본인에게 지정된 인계인수 업무      
    //목록 선택→ 업무의 상세내용 확인 후 인수 확인
	@GetMapping("/takeover/list")
    public String takeOverList(Model model) {
		List<TakeOver> takeOverList = takeOverService.getList();
		log.debug("takeOverList size:"+takeOverList.size());
		/*
		 * for (TakeOver takeOver : takeOverList) { log.debug(takeOver.toString()); }
		 */
	    model.addAttribute("list", takeOverList);
        return "takeover_list";
    }
	
	//입회목록
	//입회자가 입회함에서 본인에게 지정된 인계인수 업무      
    //목록 선택→ 업무의 상세내용 검토 확인 후 입회 확인
	@GetMapping("/observe/list")
    public String observeList(Model model) {
		List<TakeOver> observeList = takeOverService.getList();
		log.debug("observeList size:"+observeList.size());
		/*
		 * for (TakeOver takeOver : observeList) { log.debug(takeOver.toString()); }
		 */
	    model.addAttribute("list", observeList);
        return "observe_list";
    }
	
	//인수인계목록
	//조회 기능: 부서 업무인계인수내역 조회, 해당업무 상세내역 확인 
	@GetMapping("/history/list")
    public String historyList(Model model) {
		//TODO: 부서조건 추가필요
		List<TakeOver> historyList = takeOverService.getList();
		log.debug("historyList size:"+historyList.size());
		/*
		 * for (TakeOver takeOver : historyList) { log.debug(takeOver.toString()); }
		 */
	    model.addAttribute("list", historyList);
        return "history_list";
    }
	
	@GetMapping(value = "/takeover/detail/{id}")
    public String detail(Model model, @PathVariable("id") String id) {
        TakeOver takeOver = takeOverService.getTakeOver(id);
        model.addAttribute("takeOver", takeOver);
        return "takeover_detail";
    }
	
	//인계신청
	//인계자가 인계신청서를 작성 한다.
	@GetMapping("/takeover/registration")
    public String takeOverRegist(Model model) {
        return "takeover_registration";
    }
	
}
