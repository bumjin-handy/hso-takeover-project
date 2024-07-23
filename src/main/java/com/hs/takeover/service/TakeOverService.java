package com.hs.takeover.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.hs.takeover.domain.TakeOver;
import com.hs.takeover.repository.TakeOverRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TakeOverService {

    private final TakeOverRepository takeOverRepository;

    public List<TakeOver> getList() {
        return this.takeOverRepository.findAll();
    }
}