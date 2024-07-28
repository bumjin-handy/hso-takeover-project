package com.hs.takeover.service;


import com.hs.takeover.domain.TakeOver;
import com.hs.takeover.exceptions.DataNotFoundException;
import com.hs.takeover.repository.TakeOverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TakeOverService {

    private final TakeOverRepository takeOverRepository;

    public List<TakeOver> getList() {
        return this.takeOverRepository.findAll();
    }

    public TakeOver getTakeOver(String id) {
        Optional<TakeOver> takeOver = takeOverRepository.findById(id);
        if (takeOver.isPresent()) {
            return takeOver.get();
        } else {
            throw new DataNotFoundException("takeOver not found");
        }
    }
}