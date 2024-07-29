package com.hs.takeover.service;

import com.hs.takeover.domain.TakeOver;
import com.hs.takeover.repository.TakeOverRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TakeOverServiceTest {

    @Mock
    private TakeOverRepository takeOverRepository;

    @InjectMocks
    private TakeOverService takeOverService;

    @Test
    void getList_ShouldReturnAllTakeOvers() {
        // Given
        TakeOver takeOver1 = new TakeOver(); // 필요한 경우 takeOver1의 속성 설정
        TakeOver takeOver2 = new TakeOver(); // 필요한 경우 takeOver2의 속성 설정
        List<TakeOver> expectedTakeOvers = Arrays.asList(takeOver1, takeOver2);

        when(takeOverRepository.findAll()).thenReturn(expectedTakeOvers);

        // When
        List<TakeOver> result = takeOverService.getList();

        // Then
        assertEquals(expectedTakeOvers, result);
        verify(takeOverRepository, times(1)).findAll();
    }

    @Test
    void getList_ShouldReturnEmptyList_WhenNoTakeOversExist() {
        // Given
        when(takeOverRepository.findAll()).thenReturn(Collections.emptyList());//List.of()

        // When
        List<TakeOver> result = takeOverService.getList();

        // Then
        assertTrue(result.isEmpty());
        verify(takeOverRepository, times(1)).findAll();
    }
}