package com.hs.takeover.contoller;

import com.hs.takeover.domain.TakeOver;
import com.hs.takeover.service.TakeOverService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TakeOverController.class)
class TakeOverControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TakeOverService takeOverService;

    @DisplayName("인계 데이터 가져오기 테스트")
    @Test
    void handOverList() throws Exception{
        List< TakeOver > handOverList = new ArrayList<TakeOver>();
        TakeOver takeOver = new TakeOver();
        takeOver.setHandOverUserName("handOverUser1");
        takeOver.setTakeOverUserName("takeOverUser1");
        takeOver.setObserveUserName("observeUser1");
        handOverList.add(takeOver);

        given(takeOverService.getList()).willReturn(
                handOverList
        );
        mockMvc.perform(get("/handover/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("handover_list"))
                .andExpect(model().attribute("list", hasSize(1)))
                .andExpect(model().attribute("list", hasItems(hasProperty("handOverUserName", is("handOverUser1")))))
                .andExpect(model().attribute("list", hasItem(
                        allOf(
                                hasProperty("handOverUserName", is("handOverUser1")),
                                hasProperty("takeOverUserName", is("takeOverUser1")),
                                hasProperty("observeUserName", is("observeUser1"))
                        )
                )))
                .andDo(print());

        verify(takeOverService).getList();
    }

    @DisplayName("인수 데이터 가져오기 테스트")
    @Test
    void takeOverList() throws Exception{
        List< TakeOver > takeOverList = new ArrayList<TakeOver>();

        given(takeOverService.getList()).willReturn(
                takeOverList
        );
        mockMvc.perform(get("/takeover/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("takeover_list"))
                .andExpect(model().attribute("list", hasSize(0)))
                .andDo(print());

        verify(takeOverService).getList();
    }

    @DisplayName("입회 데이터 가져오기 테스트")
    @Test
    void observeList() throws Exception{
        List< TakeOver > observeList = new ArrayList<TakeOver>();

        given(takeOverService.getList()).willReturn(
                observeList
        );
        mockMvc.perform(get("/observe/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("observe_list"))
                .andExpect(model().attribute("list", hasSize(0)))
                .andDo(print());

        verify(takeOverService).getList();
    }

    @DisplayName("인수인계 데이터 가져오기 테스트")
    @Test
    void historyList() throws Exception{
        List< TakeOver > historyList = new ArrayList<TakeOver>();

        given(takeOverService.getList()).willReturn(
                historyList
        );
        mockMvc.perform(get("/history/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("history_list"))
                .andExpect(model().attribute("list", hasSize(0)))
                .andDo(print());

        verify(takeOverService).getList();
    }
}