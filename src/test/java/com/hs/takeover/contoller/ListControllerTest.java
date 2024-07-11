package com.hs.takeover.contoller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@DisplayName("View 컨트롤러 - 업무인수인계")
@WebMvcTest(ListController.class)
public class ListControllerTest {

	    private final MockMvc mvc;

	    public ListControllerTest(@Autowired MockMvc mvc) {
	        this.mvc = mvc;
	    }

	    @DisplayName("[view][GET] 리스트 페이지 - 정상 호출")
	    @Test
	    public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
	        // Given

	        // When & Then
	        mvc.perform(get("/list"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
	                .andExpect(view().name("list"))
	                .andExpect(model().attributeExists("lists"));
	    }
	}
