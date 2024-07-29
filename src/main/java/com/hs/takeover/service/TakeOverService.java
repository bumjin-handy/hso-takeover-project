package com.hs.takeover.service;


import com.hs.takeover.domain.TakeOver;
import com.hs.takeover.repository.TakeOverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TakeOverService {

    private final TakeOverRepository takeOverRepository;

    public List<TakeOver> getList() {
        return this.takeOverRepository.findAll();
    }
}